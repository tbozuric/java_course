/**
 * The method using an AJAX call creates buttons to display tagged images.
 */
function createImageTagsButtons() {
    $.ajax({
        url: "rest/image-tags",
        dataType: "json",
        success: function (data) {
            let tags = data;
            let html = "";
            if (tags.length === 0) {
                html = "No available tags.";
                $(".imageTagsContainer").html(html);

            } else {
                let div = document.createElement('div');
                div.className = 'tagBtn';

                for (let i = 0; i < tags.length; i++) {
                    let value = escapeHtml(tags[i]);
                    let button = document.createElement('button');
                    button.onclick = function () {
                        processTagClick(value);
                    };
                    button.innerHTML = value;
                    button.className = 'btn';
                    div.appendChild(button);

                }

                let thumbnailDiv = document.createElement("div");
                thumbnailDiv.className = 'thumbnailImage';

                let polaroid = document.createElement("div");
                polaroid.className = 'polaroid';
                polaroid.style.display = "none";

                let imagePart = document.createElement("div");
                imagePart.className = 'fullImage';

                let container = document.createElement("div");
                container.className = "container";


                let descriptionPart = document.createElement("p");
                descriptionPart.id = 'description';

                let tagsPart = document.createElement("p");
                tagsPart.id = 'tags';

                container.appendChild(descriptionPart);
                container.appendChild(tagsPart);
                polaroid.appendChild(imagePart);
                polaroid.appendChild(container);
                div.appendChild(thumbnailDiv);
                div.appendChild(polaroid);
                $(".imageTagsContainer").append(div);
            }
        }
    })
}

/**
 *This method processes clicks by showing thumbnails associated with a tag.
 * @param tagName the image tag.
 */
function processTagClick(tagName) {
    $(".thumbnailImage").html("");
    $(".polaroid").hide();

    $.ajax({
        url: "rest/images/" + tagName,
        dataType: "json",
        success: function (data) {
            let images = data;
            if (images.length === 0) {
                const html = "No available images";
                $(".thumbnailImage").html(html);
            }
            else {
                for (let i = 0; i < images.length; i++) {
                    let value = images[i];
                    let img = document.createElement('img');
                    img.src = "rest/thumbnail/" + value;
                    img.className = "thumbnailImg";
                    $(".thumbnailImage").append(img);
                    img.onclick = function () {
                        showImage(value, tagName);
                    };
                }
            }
        }
    })
}

/**
 * The method displays an image in its original dimensions.
 * @param imageName the image name.
 * @param tagName the tag name.
 */
function showImage(imageName, tagName) {
    $(".polaroid").hide();
    $.ajax({
        url: "rest/image/info/" + tagName + "/" + imageName,
        dataType: "json",
        success: function (data) {

            let fullImage = document.createElement('img');
            fullImage.src = "rest/image/" + imageName;

            let element = $(".fullImage");
            element.html("");
            element.append(fullImage);

            let description = data['description'];
            let tags = data['tags'];
            $("#description").text(escapeHtml(description));
            let stringTags = "";
            for (let i = 0; i < tags.length; i++) {
                stringTags += "#" + escapeHtml(tags[i]);
            }
            $("#tags").text(stringTags);
            $(".polaroid").show()
            $("html, body").animate({ scrollTop: $(document).height() }, 1000);
        },
    })
}
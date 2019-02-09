
// an array of colors and assign it to a variable colors
var colors = [ "22ac5e", "d68236", "71b5c2", "af2655", "b34de7", "e6bd01", "104393", "ca4d94", "4a772d", "c180a7", "958112", "8d2f8d" ];
var currentColor = colors[Math.floor(Math.random()*colors.length)];
// sets the preview color to the one entered in the input and display its color code using setPreviewColor function
function setPreviewColor(color) {
    $('.preview').css('background-color', color);
    $('.color-code').text($('.preview').css('background-color'));
}
//adds color boxes to the favorite colors
function addBox(color) {
    $('#colors').prepend("<div class='item' style='background-color: " + color + ";'><div>");
}

$(document).ready(function(){
    //1.As the page loads add each color in the colors array to the div '#colors'
    for (index = 0; index < colors.length; index++){
        addBox(colors[index]);
    }

//set the preview color to one of the colors in the colors array randomly
    setPreviewColor(currentColor);

// an event handler for the key up event i.e. when the user types the color in the input and releases the key on the keyboard
//The event should set the preview color to the color typed in the input
    $(document).on('keydown keyup keypress', '#color', function(){
        currentColor = $(this).val();
        setPreviewColor(currentColor);
    })
//2.Write an event handler to handle the click the event on the add to favorite button so that the color gets added to the list of favorite colors,
// the content of the input gets cleared and the focus gets back on the input
    $(document).on('click', '#add-to-favorite', function(event){
        for (i = 0; i < colors.length; i++){
            //don't wanna add a color that has already been favorited
            if (colors[i] === currentColor) return;
        }
        addBox(currentColor);
        let el = document.getElementById('color');
        el.value = "";
        el.focus();
    })

//3.Write events handlers such that whenever any item in the favorite colors is clicked or hovered, the color gets displayed in the preview div
    $(document).on('click', '.item', function(event){
        currentColor = $(this).css("background-color");
        setPreviewColor(currentColor);
        event.stopPropagation();
        event.stopImmediatePropagation();
    })

    $(document).on('mouseover', '.item', function(event){
        let tempColor = $(this).css("background-color");
        setPreviewColor(tempColor);
        event.stopPropagation();
        event.stopImmediatePropagation();
    })

    $(document).on('mouseover', '#colors', function(event){
        let tempColor = $(this).css("background-color");
        setPreviewColor(tempColor);
        event.stopPropagation();
        event.stopImmediatePropagation();
    })

    $(document).on('mouseout', '#colors', function(event){
        setPreviewColor(currentColor);
        event.stopPropagation();
        event.stopImmediatePropagation();
    })
});

let popup;

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("exList").onclick = () => {
        popup = window.open("/exercise/list", "exerciseList", "width=800,height=576");
        popup.document.addEventListener("DOMContentLoaded", () => {
            popup.document.querySelectorAll('button[data="chooseBtn"]').forEach(i => {
                i.onclick = () => {

                };
            });
        });
    };
});
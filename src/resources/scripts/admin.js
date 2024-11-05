document.addEventListener("DOMContentLoaded", () => {
    let popup = null,
        onchangeTimer = null;
    const idInput = document.getElementById('idInput');

    document.getElementById("exList").onclick = () => {
        popup = window.open("/exercise/list", "exerciseList", "width=800,height=576");
        let check = setInterval(() => {
            if (popup.document.body.innerHTML == "")
                return;
            clearInterval(check);
            popup.document.querySelectorAll('button[data="chooseBtn"]').forEach(i => {
                i.onclick = () => {
                    idInput.value = i.getAttribute('data-id');
                    idInput.dispatchEvent(new Event('change'));
                    idInput.parentElement.classList.remove('is-loading');
                    popup.window.close();
                };
            });
        }, 100);
    };

    document.getElementById('idInput').onchange = e => {
        clearTimeout(onchangeTimer);
        e.target.parentElement.classList.remove('is-loading');
        onchangeTimer = setTimeout(async () => {
            e.target.parentElement.classList.add('is-loading');
            console.log(await (await fetch('/exercise?id=' + idInput.value)).json());
            e.target.parentElement.classList.remove('is-loading');
        }, 1000)
    };
});
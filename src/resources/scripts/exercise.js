let editor = ace.edit("editor");
editor.setTheme("ace/theme/dracula");
let sessions = {};
document.querySelectorAll('li').forEach(i => {
    let session = new ace.EditSession(`class ${i.innerText.split('.')[0]} {

}`);
    session.setMode("ace/mode/java");
    sessions[i.innerText] = session;
    setTab(document.querySelector('li'));
});
editor.setOptions({
    'showPrintMargin': false,
    'fontFamily': 'JetBrains Mono',
    'fontSize': '.96em'
});

function setTab(elem) {
    editor.setSession(sessions[elem.innerText]);
    let active = document.querySelector('li.is-active');
    if (active)
        active.classList.remove('is-active');
    elem.classList.add("is-active");
}
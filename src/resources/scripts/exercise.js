let editor = ace.edit("editor");
editor.setTheme("ace/theme/tomorrow_night_bright");
let sessions = {};
document.querySelectorAll('li').forEach(i => {
    let session = new ace.EditSession(`class ${i.innerText.split('.')[0]} {

}`);
    session.setMode("ace/mode/java");
    sessions[i.innerText.replace(/\.[^/.]+$/, "")] = session;
    setTab(document.querySelector('li'));
});
editor.setOptions({
    'showPrintMargin': false,
    'fontFamily': 'JetBrains Mono',
    'fontSize': '.96em'
});

function setTab(elem) {
    editor.setSession(sessions[elem.innerText.replace(/\.[^/.]+$/, "")]);
    let active = document.querySelector('li.is-active');
    if (active)
        active.classList.remove('is-active');
    elem.classList.add("is-active");
}

const objectMap = (obj, fn) =>
  Object.fromEntries(
    Object.entries(obj).map(
      ([k, v], i) => [k, fn(v, k, i)]
    )
  )

async function checkResults(button) {
    button.classList.toggle("is-loading");
    //button.setAttribute('disabled', '');
    let classes = objectMap(sessions, x => x.getValue());
    console.log(classes);
    const { stdout, stderr } = await (await fetch('/exercise', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            'id': '0',
            ...classes
        })
    })).json();
    const notification = document.getElementById("notification");
    const elems = [];
    let elem;

    elem = createElem('h4', 'Вывод программы:', ['title', 'is-4']);
    elems.push(elem);

    elem = createElem('pre', stdout.replace('SUCCESS:TRUE!', ''));
    elems.push(elem);
    elems.push(createElem('br'));

    elem = createElem('h4', 'Ошибки:', ['title', 'is-4']);
    elems.push(elem);

    elem = createElem('pre', stderr);
    elems.push(elem);

    elems.forEach(x => notification.append(x));
    if (stdout.includes('SUCCESS:TRUE!'))
        notification.classList.add("is-success");
    else
        notification.classList.add("is-danger");
    notification.style.display = 'block';
    button.classList.toggle("is-loading");
}

function createElem(tag, text, classes = []) {
    const elem = document.createElement(tag);
    elem.innerText = text;
    classes.forEach(c => elem.classList.add(c));
    return elem;
}
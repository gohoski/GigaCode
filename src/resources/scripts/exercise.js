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
    let classes = objectMap(sessions, x => x.getValue());
    const json = await (await fetch('/exercise', {
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
    notification.innerHTML = "";
    notification.classList.remove('is-danger', 'is-success');

    const elems = [];
    //let elem;

    if (json.success) {
        if (json.stdout.includes('SUCCESS:TRUE!')) {
            notification.classList.add("is-success");
            elems.push(createElem('h4', 'Вывод программы:', ['title', 'is-4']));

            elems.push(createElem('pre', json.stdout.replace('SUCCESS:TRUE!', '')));
            elems.push(createElem('br'));

            let a = createElem('a', '<b>Следующее задание</b>', ['button','is-info'], false);
            a.onclick = () => {
                a.classList.add('is-loading');
                window.location.reload();
            };
            elems.push(a);
        } else {
            notification.classList.add("is-danger");
            elems.push(createElem('h4', `Ошибка${json.stderr.length ? ` <code>${json.stderr}</code>` : ""}, проверьте свой код`, ['title', 'is-4'], false));
        }
    } else {
        notification.classList.add("is-danger");
        elems.push(createElem('h4', json.errorMessage, ['title', 'is-4']));
        elems.push(createElem('pre', json.errorTrace.join("\n")));
    }

    elems.forEach(x => notification.append(x));
    notification.style.display = 'block';
    button.classList.toggle("is-loading");
}

function createElem(tag, text, classes = [], innerText = true) {
    const elem = document.createElement(tag);
    if (innerText)
        elem.innerText = text;
    else
        elem.innerHTML = text;
    classes.forEach(c => elem.classList.add(c));
    return elem;
}
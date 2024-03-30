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
    if (stdout.includes('SUCCESS:TRUE!')) {
        const elems = [];
        let elem;
        elem = document.createElement('h4');
        elem.innerText = 'Вывод программы:'
        elem.classList.add('title');
        elem.classList.add('is-4');
        elems.push(elem);

        elem = document.createElement('pre');
        elem.innerText = stdout.replace('SUCCESS:TRUE!', '');
        elems.push(elem);

        elems.push(document.createElement('br'));

        elem = document.createElement('h4');
        elem.innerText = 'Ошибки:'
        elem.classList.add('title');
        elem.classList.add('is-4');
        elems.push(elem);

        elem = document.createElement('pre');
        elem.innerText = stderr;
        elems.push(elem);

        elems.forEach(x => notification.append(x));

        notification.classList.add("is-success");
        notification.style.display = 'block';
    }
    button.classList.toggle("is-loading");
}
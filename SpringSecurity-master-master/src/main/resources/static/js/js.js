function getParent(obj, parentTagName) {
    return (obj.tagName === parentTagName) ? obj : getParent(obj.parentNode, parentTagName);
}

function dataTransfer(parentNode, idForm) {
    const tds = parentNode.getElementsByTagName('TD');
    const id = tds[0].innerHTML;
    const tdRole = 5;
    const currentRolesUser = parentNode.cells[tdRole].getElementsByTagName("SPAN");
    const form = document.getElementById(idForm);
    const inputs = form.getElementsByTagName("INPUT");
    const options = form.getElementsByTagName("SELECT")[0].options;


    //в атрибут action для формы добавляем id пользователя
    form.action += id;

    //сброс selected
    for (let o = 0; o < options.length; o++) {
        options[o].selected = false;
    }

    //заполнение полей формы editUser-form
    for (let c = 0; c < tds.length - 2; c++) {
        if (inputs[c + 1].name !== 'password') {
            inputs[c + 1].value = tds[c].innerHTML;
        }
        if (inputs[c + 1].name !== '_roles') {
            for (let i = 0; i < options.length; i++) {
                for (let j = 0; j < currentRolesUser.length; j++) {
                    if (options[i].text === currentRolesUser[j].innerHTML.trim()) {
                        options[i].selected = true;
                    }
                }
            }
        }
    }
}
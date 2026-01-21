function saveImprovementRequest(message) {
    $.ajax({
        url: apiURL + '/requests',
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        cache: false,
        data: JSON.stringify({
            // message: message,
            user: localStorage.getItem('username'),
            requestDetailDTO: [{
                typ: 'IMPROVEMENT',
                text: message,
            }]
        }),

        success: function (data, response) {
            notifySuccess('Merci pour votre avis')
            let textarea = $('#deTextArea').dxTextArea('instance');
            if (textarea) textarea.reset();
        }, error: function (e, response, error) {
            let errorResponse = e.responseJSON;
            notifyError(errorResponse.message);
        }, beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', localStorage.getItem('eGlobalToken'));
        }
    })
}

$(() => {
    const textArea = $('#deTextArea').dxTextArea({
        value: '',
        height: '20vh',
    }).dxTextArea('instance');

    const sendButton = $('#deButton').dxButton({
        icon: "chevronnext",
        hint: 'Reset filtres',
        hoverStateEnabled: true,
        text: "Envoyer",
        onClick() {
            let message = textArea.option('value');
            console.log(message);
            if (message && message.trim().length > 0) {
                saveImprovementRequest(message);
            } else {
                notifyError('Le message est vide');
            }
        }
    }).dxButton('instance');
});
var taskApi = Vue.resource('/task');
var getHistoryApi = Vue.resource('/task/history');




Vue.component('valutes-list',  {
    props: ['valutes','test','test2', 'amount', 'els', 'resp', 'histobjs' ],
    template:
        '<div class="main">' +
        ' <h>Выберете валюту для конвертации</h>' +
        '  <select v-model="test">' +
        '        <option  v-for="valute in valutes"> {{valute.name}} </option>' +
        '    </select>' +
        ' <h>Введите сумму</h>' +
        '<input placeholder="Введите сумму" v-model="amount"/>' +
        ' <h>Выберите необходимую валюту</h>' +
        '  <select v-model="test2">' +
        '        <option  v-for="valute in valutes"> {{valute.name}} </option>' +
        '    </select>' +
        '    <button @click="save">Конвертировать</button>' +
        ' <div class="resp">Результат конвертации: {{resp}}</div>' +
        ' <h></h>' +
        ' <h3>Истоия запросов</h3>' +
        '<table> ' +
        '<th>Дата операции</th>' +
        '<th>Выбранная валюта</th>' +
        '<th>Количество (ед.)</th>' +
        '<th>Необходимая валюта</th>' +
        '<th>Итог конвертации (руб.)</th>' +
        '</tr>' +
        '<tr v-for="historyobj in histobjs">' +
        '<td>{{historyobj.date}}</td>' +
        '<td>{{historyobj.firstName}}</td>' +
        '<td>{{historyobj.amount}}</td>' +
        '<td>{{historyobj.secondName}}</td>' +
        '<td>{{historyobj.result}}</td>' +
        '</tr>' +
        '</table>' +
        '</div>',

    methods:{
        save: function () {
            var el ={firstName: this.test, secondName: this.test2, amount: this.amount};

            taskApi.save({}, el).then(result =>
                this.resp = result.bodyText
            )
            //обновляем историю конвертаций
            this.histobjs = [];
            getHistoryApi.get().then(result =>
                result.json().then(data =>
                    data.forEach(element => this.histobjs.push(element)))
            )
        }
    },
    created: function () {
        taskApi.get().then(result =>
            result.json().then(data =>
                data.forEach(element => this.valutes.push(element)))
        )
        getHistoryApi.get().then(result =>
            result.json().then(data =>
                data.forEach(element => this.histobjs.push(element)))
        )

    }


})




var app = new Vue({
    el: '#app',
    template: '<valutes-list :valutes="valutes" :histobjs="histobjs" />',
    data: {
        valutes: [],
        els: [],
        test: '',
        test2: '',
        amount: '',
        resp: '',
        user:'',
        histobjs: []
    }
});
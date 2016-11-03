<template>
  <div id="app">
    <va-header></va-header>
    <message-list :messages="messages"></message-list>
    <form class="actionBar" @submit.prevent="onSubmit" @keyup.enter.prevent="onSubmit">
      <input class="inputbox" v-model="currentText" />
      <!-- <button type="button" @click="onSubmit">send</button> -->
      <a class="button" :class="{active: currentText.length}" @click.stop="onSubmit">
        <i class="fa fa-paper-plane" aria-hidden="true"></i>
      </a>
    </form>
  </div>
</template>

<script>
import _ from 'lodash'
import moment from 'moment'
import 'moment/locale/fr-ch.js'

import VaHeader from './components/Header'
import MessageList from './components/MessageList'
import {sendMessage} from './services/api'

moment.locale('fr-ch')

export default {
  components: {
    VaHeader,
    MessageList
  },
  data () {
    return {
      currentText: '',
      messages: [
        // {id: '1', timestamp: 1478095424843, text: 'Hello', me: true},
        // {id: '2', timestamp: 1478095445843, text: 'Hi, can I help?', me: false},
        // {id: '3', timestamp: 1478095464843, text: 'Yeah.', me: true},
        // {id: '4', timestamp: 1478095524843, text: 'How?', me: false},
        // {id: '5', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true},
        // {id: '6', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true},
        // {id: '7', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true},
        // {id: '8', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true}
      ]
    }
  },
  methods: {
    onSubmit () {
      let text = this.currentText
      if (!text) {
        return
      }
      this.messages = this.messages.concat({
        id: (Math.random() + '').substr(3, 10),
        timestamp: moment().valueOf(),
        text,
        me: true
      })
      this.currentText = ''
      sendMessage(text).then((result) => {
        let response = _.get(result, 'result.fulfillment.speech')
        if (response) {
          this.messages = this.messages.concat({
            id: result.id,
            timestamp: moment().valueOf(),
            text: response,
            me: false
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
html, body {
  height: 100%;
}

.actionBar {
  margin: 10px 20px;
  min-height: 30px;
  display: flex;

  .inputbox {
    border: 1px solid #007d32;
    border-radius: 5px;
    padding: 2px 5px;
    flex: 1 1 auto;
    align-self: auto;
    margin-right: 10px;

    &:focus {
      outline-width: 0;
    }
  }

  .button {
    color: #777;
    padding: 5px;

    &.active {
      color: #007d32;
      cursor: pointer;
    }
  }
}

#app {
  height: 100%;
  display: flex;
  flex-direction: column;
  font-family: Source Sans Pro, Helvetica, sans-serif;
}

::-webkit-scrollbar {
    width: 12px;
}

::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    border-radius: 10px;
}

::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5);
}
</style>

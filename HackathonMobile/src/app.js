import React, { Component } from 'react'
import {
  StyleSheet,
  ListView,
  TextInput,
  TouchableHighlight,
  View
} from 'react-native'
import Icon from 'react-native-vector-icons/FontAwesome'

import _ from 'lodash'
import moment from 'moment'
import 'moment/locale/fr-ch.js'

import Header from './comps/Header.js'
import MessageList from './comps/MessageList.js'
import { sendMessage } from './services/api.js'

class app extends Component {
  constructor (props) {
    super(props)

    moment.locale('fr-ch')

    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.id !== r2.id})

    this._messages = [
      // {id: '1', timestamp: 1478095424843, text: 'Hello', me: true},
      // {id: '2', timestamp: 1478095445843, text: 'Hi, can I help?', me: false},
      // {id: '3', timestamp: 1478095464843, text: 'Yeah.', me: true},
      // {id: '4', timestamp: 1478095524843, text: 'How?', me: false},
      // {id: '5', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true},
      // {id: '6', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true},
      // {id: '7', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true},
      // {id: '8', timestamp: 1478095544843, text: 'I\'m stuck with these crappy native style attributes that I can\'t seem to wrap my head around!', me: true}
    ]

    this.state = {
      currentText: '',
      dataSource: ds.cloneWithRows(this._messages)
    }

    sendMessage('bonjour').then((result) => {
      let response = _.get(result, 'result.fulfillment.speech')
      if (response) {
        this._messages = this._messages.concat({
          id: result.id,
          timestamp: moment().valueOf(),
          text: response,
          me: false
        })
        this.setState({
          dataSource: this.state.dataSource.cloneWithRows(this._messages)
        })
      }
    })
  }

  componentDidMount () {
    // let self = this
  }

  render () {
    const { currentText, dataSource } = this.state
    return (
      <View style={styles.main}>
        <Header />
        <MessageList style={styles.list} dataSource={dataSource} />
        <View style={styles.actionBar}>
          <TextInput style={styles.input} underlineColorAndroid={'#007d32'} selectionColor={'#007d32'}
            value={currentText}
            onChangeText={(currentText) => this.setState({currentText})}
            onSubmitEditing={() => {
              this._submit(currentText, dataSource)
            }}
          />
          <TouchableHighlight
            activeOpacity={0.8}
            underlayColor={'rgba(0, 128, 51, 0.25)'}
            style={styles.button}
            disabled={!currentText.length}
            onPress={() => {
              this._submit(currentText, dataSource)
            }}
          >
            <Icon style={[styles.icon, (currentText.length ? '' : styles.disabled)]} name='send' size={20} />
          </TouchableHighlight>
        </View>
      </View>
    )
  }

  _submit (text, dataSource) {
    if (!text) {
      return
    }
    this._messages = this._messages.concat({
      id: (Math.random() + '').substr(3, 10),
      timestamp: moment().valueOf(),
      text,
      me: true
    })
    this.setState({
      currentText: '',
      dataSource: dataSource.cloneWithRows(this._messages)
    })
    sendMessage(text).then((result) => {
      let response = _.get(result, 'result.fulfillment.speech')
      if (response) {
        this._messages = this._messages.concat({
          id: result.id,
          timestamp: moment().valueOf(),
          text: response,
          me: false
        })
        this.setState({
          dataSource: dataSource.cloneWithRows(this._messages)
        })
      }
    })
  }
}

const styles = StyleSheet.create({
  main: {
    flex: 1
  },
  actionBar: {
    flexDirection: 'row',
    height: 40
  },
  input: {
    flex: 1,
    color: '#6d6b6b'
  },
  icon: {
    color: '#007d32'
  },
  disabled: {
    color: '#777'
  },
  button: {
    width: 40,
    padding: 10,
    marginRight: 5,
    borderRadius: 20,
    alignSelf: 'flex-end'
  }
})

export default app

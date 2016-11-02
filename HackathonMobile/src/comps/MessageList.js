'use strict'

import React, { Component } from 'react'
import {
  StyleSheet,
  ListView,
  Image,
  Text,
  View
} from 'react-native'

import moment from 'moment'

class MessageList extends Component {
  render () {
    const { dataSource } = this.props
    let _listView
    if (dataSource.getRowCount()) {
      return (
        <View style={styles.wrapper}>
          <ListView
            ref={(listView) => { _listView = listView }}
            onContentSizeChange={(contentWidth, contentHeight) => {
              _listView.scrollTo({y: contentHeight})
            }}
            dataSource={dataSource}
            renderRow={(message) => {
              return (
                <View style={[styles.message, (message.me ? styles.left : styles.right)]} key={message.id}>
                  <View style={styles.messageHeader}>
                    <Image style={[styles.logo, (message.me ? {height: 0} : {})]} source={require('../assets/va-logo-notext-green.png')} />
                    <Text style={styles.time}>{moment(message.timestamp).format('LT')}</Text>
                  </View>
                  <Text style={styles.text}>{message.text}</Text>
                </View>
              )
            }}
            contentContainerStyle={styles.scrollContainer} />
        </View>
      )
    } else {
      return (
        <View style={[styles.wrapper, styles.promptWrapper]}>
          <Text style={styles.prompt}>Bonjour, comment pouvons nous vous aider?</Text>
        </View>
      )
    }
  }
}

MessageList.propTypes = {
  dataSource: React.PropTypes.object.isRequired
}

const styles = StyleSheet.create({
  wrapper: {
    margin: 5,
    flex: 1,
    borderColor: '#007d32',
    borderWidth: 1,
    flexDirection: 'column',
    justifyContent: 'flex-end'
  },
  scrollContainer: {
    paddingTop: 5
  },
  messageHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between'
  },
  message: {
    borderColor: '#007d32',
    borderWidth: 1,
    borderRadius: 3,
    padding: 5,
    marginBottom: 5,
    marginTop: 5
  },
  logo: {
    resizeMode: 'contain',
    height: 10,
    width: 10
  },
  time: {
    fontSize: 10
  },
  text: {
    color: '#6d6b6b'
  },
  right: {
    marginLeft: 60,
    marginRight: 10
  },
  left: {
    marginLeft: 10,
    marginRight: 60,
    backgroundColor: 'rgba(0, 128, 51, 0.1)'
  },
  promptWrapper: {
    justifyContent: 'space-around'
  },
  prompt: {
    textAlign: 'center'
  }
})

module.exports = MessageList

'use strict'

import React, { Component } from 'react'
import {
  StyleSheet,
  ListView,
  Text,
  View
} from 'react-native'

import moment from 'moment'

class MessageList extends Component {
  render () {
    const { dataSource } = this.props
    let _listView
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
                <Text style={styles.time}>{moment(message.timestamp).format('LT')}</Text>
                <Text style={styles.text}>{message.text}</Text>
              </View>
            )
          }}
          contentContainerStyle={styles.scrollContainer} />
      </View>
    )
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
    borderWidth: 1
  },
  scrollContainer: {
    flexDirection: 'column',
    justifyContent: 'flex-start',
    paddingTop: 5
  },
  message: {
    borderColor: '#007d32',
    borderWidth: 1,
    borderRadius: 3,
    padding: 5,
    marginBottom: 5,
    marginTop: 5
  },
  text: {
    color: '#6d6b6b'
  },
  right: {
    marginLeft: 40,
    marginRight: 10
  },
  left: {
    marginLeft: 10,
    marginRight: 40,
    backgroundColor: 'rgba(0, 128, 51, 0.1)'
  },
  time: {
    fontSize: 10,
    alignSelf: 'flex-end'
  }
})

module.exports = MessageList

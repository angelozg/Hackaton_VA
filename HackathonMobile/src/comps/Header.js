'use strict'

import React, { Component } from 'react'
import {
  StyleSheet,
  Image,
  View
} from 'react-native'

class Header extends Component {
  render () {
    return (
      <View style={styles.header}>
        <Image style={styles.logo} source={require('../assets/va-logo.png')} />
      </View>
    )
  }
}

const styles = StyleSheet.create({
  header: {
    height: 60,
    backgroundColor: '#007d32',
    padding: 10,
    alignItems: 'center'
  },
  logo: {
    resizeMode: 'contain',
    height: 40
  }
})

module.exports = Header

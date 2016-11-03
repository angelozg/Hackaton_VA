const accessToken = '2fc4af45ff184caa8822a303836982f5'
// const baseUrl = 'https://api.api.ai/v1/'
const baseUrl = 'http://192.168.1.85:8080/server-hackaton/'

import _ from 'lodash'
import { guid } from './utils'

export function sendMessage (message) {
  return window.fetch(baseUrl + 'chat', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + accessToken
    },
    body: JSON.stringify({ q: message, lang: 'fr', sessionId: guid })
  })
  .then((response) => {
    try {
      return response.json()
    } catch (e) {
      console.log(e)
      return _.set({}, 'result.fulfillment.speech', 'Une erreur est survenue, pouvez-vous me réexpliquer?')
    }
  })
  .catch((error) => {
    console.log(error)
    return _.set({}, 'result.fulfillment.speech', 'Une erreur est survenue, pouvez-vous me réexpliquer?')
  })
}

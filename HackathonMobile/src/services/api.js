const accessToken = '2fc4af45ff184caa8822a303836982f5'
const baseUrl = 'https://api.api.ai/v1/'

import { guid } from './utils'

export function sendMessage (message) {
  return window.fetch(baseUrl + 'query?v=20150910', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + accessToken
    },
    body: JSON.stringify({ q: message, lang: 'fr', sessionId: guid })
  })
  .then((response) => response.json())
  .catch((error) => {
    console.error(error)
  })
}

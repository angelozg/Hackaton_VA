<template>
  <div class="list" v-el:>
    <div class="scrollWrapper" v-el:scroll-wrapper>
      <div v-if="messages.length">
        <div class="message" :class="{mine: message.me}" v-for="message in messages">
          <div class="header">
            <img v-if="!message.me" class="logo" src="../assets/va-logo-notext-green.png">
            <span class="time">{{message.timestamp | time}}</span>
          </div>
          <div class="text">{{message.text}}</text>
        </div>
      </div>
    </div>
    <div v-else class="prompt">
      Bonjour, comment pouvons nous vous aider?
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  props: ['messages'],
  watch: {
    'messages' () {
      let target = this.$els.scrollWrapper.lastElementChild
      this.$els.scrollWrapper.parentNode.scrollTop = this.$els.scrollWrapper.offsetHeight
    }
  },
  filters: {
    time (value) {
      return moment(value).format('LT')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.list {
  border: 2px solid #007d32;
  border-radius: 10px;
  margin: 10px;
  flex: 1 1 auto;
  overflow-y: scroll;
  min-height: 0;

  .scrollWrapper {
    overflow: auto;
  }

  .prompt {
    padding-top: 100px;
    padding-bottom: 100px;
    text-align: center;
  }

  .message {
    margin: 10px;
    border: 1px solid #007d32;
    padding: 5px;
    border-radius: 5px;
    float: right;
    width: 70%;

    &.mine {
      float: left;
      background-color: rgba(0, 128, 51, 0.1);
    }

    .header {
      padding: 5px;
      // border-bottom: 1px solid rgba(0, 128, 51, 0.1);
      margin-bottom: 5px;
      overflow-y: auto;

      .logo {
        height: 1em;
      }

      .time {
        float: right;
        font-size: 0.8em;
      }
    }

    .text {
      margin-left: 5px;
    }
  }
}
</style>

import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Game from '@/components/Game'
import SignUp from '@/components/SignUp'
import MatchHistory from '@/components/MatchHistory'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/game',
      name: 'Game',
      component: Game
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp
    }
    ,
    {
      path: '/matchhistory',
      name: 'MatchHistory',
      component: MatchHistory
    }
  ]
})

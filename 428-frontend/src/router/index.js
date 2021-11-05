import Vue from 'vue'
import Router from 'vue-router'
import SignIn from '@/components/SignIn'
import Home from '@/components/Home'
import Settings from '@/components/Settings'
import ECSE428 from '@/components/ECSE428'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'SignIn',
      component: SignIn
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/settings',
      name: 'Settings',
      component: Settings
    },
    {
      path: '/app',
      name: 'ECSE428',
      component: ECSE428
    }
  ]
})

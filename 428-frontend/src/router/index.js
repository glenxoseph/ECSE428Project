import Vue from 'vue'
import Router from 'vue-router'
import SignIn from '@/components/SignIn'
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
      path: '/app',
      name: 'ECSE428',
      component: ECSE428
    }
  ]
})

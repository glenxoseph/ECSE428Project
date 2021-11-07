import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ECSE428 from '@/components/ECSE428'
import ViewMatchHistory from '@/components/ViewMatchHistory'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'ECSE428',
      component: ECSE428
    },
    {
      path: '/history',
      name: 'ViewMatchHistory',
      component: ViewMatchHistory
    }
  ]
})

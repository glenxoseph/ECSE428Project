import Vue from 'vue'
import Router from 'vue-router'
import SignIn from '@/components/SignIn'
import Home from '@/components/Home'
import Settings from '@/components/Settings'
<<<<<<< HEAD
import ChangePassword from '@/components/ChangePassword'
import BannedAccounts from '@/components/BannedAccounts'
=======
>>>>>>> f9378628f8293ac71a4bedb02ca7fb584248d9fc
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
<<<<<<< HEAD
    },
    {
      path: '/changePassword',
      name: 'ChangePassword',
      component: ChangePassword
    },
    {
      path: '/bannedAccounts',
      name: 'BannedAccounts',
      component: BannedAccounts
=======
>>>>>>> f9378628f8293ac71a4bedb02ca7fb584248d9fc
    },
    {
      path: '/app',
      name: 'ECSE428',
      component: ECSE428
    }
  ]
})

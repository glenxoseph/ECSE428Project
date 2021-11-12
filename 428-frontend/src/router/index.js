import Vue from 'vue'
import Router from 'vue-router'
import SignIn from '@/components/SignIn'
import Home from '@/components/Home'
import Settings from '@/components/Settings'
import ChangePassword from '@/components/ChangePassword'
import BannedAccounts from '@/components/BannedAccounts'
import ECSE428 from '@/components/ECSE428'
import CreateAccount from "../components/CreateAccount";

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
      path: '/changePassword',
      name: 'ChangePassword',
      component: ChangePassword
    },
    {
      path: '/bannedAccounts',
      name: 'BannedAccounts',
      component: BannedAccounts
    },
    {
      path: '/app',
      name: 'ECSE428',
      component: ECSE428
    },
    {
      path: '/createAccount',
      name: 'CreateAccount',
      component: CreateAccount
    }
  ]
})

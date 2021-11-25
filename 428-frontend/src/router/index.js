import Vue from 'vue'
import Router from 'vue-router'
import Game from '@/components/Game'
import SignUp from '@/components/SignUp'
import MatchHistory from '@/components/MatchHistory'
import SignIn from '@/components/SignIn'
import Home from '@/components/Home'
import Settings from '@/components/Settings'
import ChangePassword from '@/components/ChangePassword'
import ChangeEmail from '@/components/ChangeEmail'
import DeleteAccount from '@/components/DeleteAccount'
import BannedAccounts from '@/components/BannedAccounts'
import CreateAccount from "../components/CreateAccount";
import QuizSelection from "../components/QuizSelection";
import SoloGame from "../components/SoloGame";

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
      path: '/changeEmail',
      name: 'ChangeEmail',
      component: ChangeEmail
    },
    {
      path: '/deleteAccount',
      name: 'DeleteAccount',
      component: DeleteAccount
    },
    {
      path: '/bannedAccounts',
      name: 'BannedAccounts',
      component: BannedAccounts
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
      path: '/matchHistory',
      name: 'MatchHistory',
      component: MatchHistory,
    },
    {
      path: '/createAccount',
      name: 'CreateAccount',
      component: CreateAccount
    },
    {
      path: '/quizSelection',
      name: 'QuizSelection',
      component: QuizSelection
    },
    {
      path: '/soloGame',
      name: 'SoloGame',
      component: SoloGame
    }
  ]
})

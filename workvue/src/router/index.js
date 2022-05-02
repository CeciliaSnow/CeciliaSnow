import { createRouter, createWebHistory } from 'vue-router'
import user from '../views/user/user.vue'
import user2 from '../views/user2/user2.vue'
import login from "../views/login";
import register from "../views/register";
import admin from "../views/admin/admin";
import layout from "../layout/layout";
import adminuser from "../views/admin/adminuser";
import userperson from "../views/user/userperson";
import userperson2 from "../views/user2/userperson2";
import position1 from "../views/user2/position1.vue";
import position2 from "../views/user2/position2.vue";
import beaccepted from "../views/user2/beaccepted.vue";
import beaccepted2 from "../views/user2/beaccepted2.vue";
import accepted from "../views/user/accepted.vue";
import accepted2 from "../views/user/accepted2.vue";
import employ from "../views/user2/employ.vue";
import employ2 from "../views/user2/employ2.vue";
import giveups0 from "../views/user2/giveups0.vue";
import giveups from "../views/user2/giveups.vue";
import giveups2 from "../views/user2/giveups2.vue";
import work from "../views/user/work.vue";
import work2 from "../views/user/work2.vue";
import giveup0 from "../views/user/giveup0.vue";
import giveup from "../views/user/giveup.vue";
import giveup2 from "../views/user/giveup2.vue";
import complete from "../views/user/complete.vue";
import complete2 from "../views/user2/complete2.vue";
import admintemp from "../views/admin/admintemp.vue"
import adminposition from "../views/admin/adminposition.vue"
import adminwork from "../views/admin/adminwork.vue"
import admincom from "../views/admin/admincom.vue"
import admingive from "../views/admin/admingive.vue"
import admingive0 from "../views/admin/admingive0.vue"
import index from "../views/home/index.vue"
import indexposition from "../views/home/indexposition.vue"
const routes = [
  {
    path: '/',
    name: 'layout',
    component: layout,
      children:[
        // 主页
        {
          path: '/',
          name: 'index',
          component: index,
        },
        {
          path: '/indexposition/:key',
          name: 'indexposition',
          component: indexposition,
        },
        {
          path: '/beaccepted2/:key',
          name: 'beaccepted2',
          component: beaccepted2,
        },
        {
          path: '/employ2/:key',
          name: 'employ2',
          component: employ2
        },
        {
          path: '/giveups2/:key',
          name: 'giveups2',
          component: giveups2
        },
        {
          path: '/accepted2/:key',
          name: 'accepted2',
          component: accepted2
        },
        {
          path: '/work2/:key',
          name: 'work2',
          component: work2
        },
        {
          path: '/giveup2/:key',
          name: 'giveup2',
          component: giveup2
        },
        // 个人工作用户
        {
          path: '/user',
          name: 'user',
          component: user,
          children:[
            {
              path: '/person',
              name: 'userperson',
              component: userperson
            },
            {
              path: '/accepted',
              name: 'accepted',
              component: accepted
            },
            {
              path: '/work',
              name: 'work',
              component: work
            },
            {
              path: '/giveup0',
              name: 'giveup0',
              component: giveup0
            },
            {
              path: '/giveup',
              name: 'giveup',
              component: giveup
            },
            {
              path: '/complete',
              name: 'complete',
              component: complete
            },
          ]
        },
        // 个人雇佣用户
        {
          path: '/user2',
          name: 'user2',
          component: user2,
          children:[
            {
              path: '/person2',
              name: 'userperson2',
              component: userperson2
            },
            {
              path: '/position1',
              name: 'position1',
              component: position1
            },
            {
              path: '/position2',
              name: 'position2',
              component: position2
            },
            {
              path: '/beaccepted',
              name: 'beaccepted',
              component: beaccepted
            },
            {
              path: '/employ',
              name: 'employ',
              component: employ
            },
            {
              path: '/giveups0',
              name: 'giveups0',
              component: giveups0
            },
            {
              path: '/giveups',
              name: 'giveups',
              component: giveups
            },
            {
              path: '/complete2',
              name: 'complete2',
              component: complete2
            },
          ]
        }
      ]
  },
  // 管理员
  {
    path: '/admin',
    name: 'admin',
    component: admin,
    children:[
      {
        path: '/adminuser',
        name: 'adminuser',
        component: adminuser
      },
      {
        path: '/admintemp',
        name: 'admintemp',
        component: admintemp
      },
      {
        path: '/adminposition',
        name: 'adminposition',
        component: adminposition
      },
      {
        path: '/adminwork',
        name: 'adminwork',
        component: adminwork
      },
      {
        path: '/admincom',
        name: 'admincom',
        component: admincom
      },
      {
        path: '/admingive',
        name: 'admingive',
        component: admingive
      },
      {
        path: '/admingive0',
        name: 'admingive0',
        component: admingive0
      },
    ]
  },
  // 登录注册
  {
    path: '/login',
    name: 'login',
    component: login
  },
  {
    path: '/register',
    name: 'register',
    component: register,
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

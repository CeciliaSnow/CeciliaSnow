import { createStore } from 'vuex'

export default createStore({
  state: {
    user:{},
    se:'',
    radio:'',
  },
  mutations: {
    SET_USER(state,user){
      state.user = user
    },
    add(state,data){
      state.se = data
    },
    ra(state,data){
      state.radio = data
    }
  },
  actions: {
    SET_USER({commit},user){
      this.state.user = user
    }
  },
  getters: {
    getUser:(state)=>state.user,
    getSe:(state)=>state.se,
    getRa: function (state) {
      return state.radio; 
    }
  }
})

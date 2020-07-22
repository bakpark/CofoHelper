<template>
    <div class="container_relative">
      <div class="container_absolute">
        <button @click="logOut">logout</button>
      </div>
    </div>
</template>
<script>
export default {
  props: {
  },
  methods: {
    logOut () {
      localStorage.removeItem('authorization')
      this.$store.state.isLoggedIn = false
      this.$router.push({path: '/'})
    }
  }
}
</script>
<style>
.container_relative{
  position: relative;
}
.container_absolute{
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    z-index: 10;
    top: 0.3em;
    right: 0;
}
</style>

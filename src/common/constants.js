const httpAuthConfig = () => ({
  headers: {
    authorization: localStorage.getItem('authorization').toString()
  }
})

export default {
  httpAuthConfig
}

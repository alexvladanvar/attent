import axios from 'axios'
axios.defaults.withCredentials = true
export const signUp = data => {
  console.log(data)
  return axios.post('http://localhost:8080/register', data)
}
export const logIn = data => axios.post('http://localhost:8080/login/', data)
export const getUser = () =>
  axios('http://localhost:8080/me/', { method: 'get', withCredentials: true })

export const getUserData = () => axios.get('http://localhost:8080/getUserData/')

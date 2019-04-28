import axios from 'axios'
const URL = 'http://localhost:8080'

axios.defaults.withCredentials = true
export const signUp = data => {
  console.log(data)
  return axios.post(`${URL}/register`, data)
}
export const logIn = data => axios.post(`${URL}/login/`, data)
export const getUser = () =>
  axios(`${URL}/me/`, { method: 'get', withCredentials: true })

export const getUserData = () => axios.get(`${URL}/getUserData/`)
export const logout = () => axios.get(`${URL}/logout/`)
export const generateQR = data => axios.post(`${URL}/generateQR/`, data)

export const getAttendanceData = () => axios.get(`${URL}/attendances/`)

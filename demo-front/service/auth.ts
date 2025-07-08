import axios from "axios";
import apiClient from "./api";

const BASE_URL = "/auth";

async function signIn(email: string, password: string) {
  try {
    const response = await apiClient.post(`${BASE_URL}/signin`, {
      email,
      password,
    });
    return response.data;
  } catch (error) {
    console.log("Error signing in:", error);
    throw error;
  }
}

async function signUp(email: string, password: string) {
  try {
    const response = await apiClient.post(`${BASE_URL}/signup`, {
      email,
      password,
    });
    return response.data;
  } catch (error) {
    console.log("Error signing up:", error);
    throw error;
  }
}

async function resetPassword(email: string) {
  try {
    const response = await apiClient.post(`${BASE_URL}/reset-password`, {
      email,
    });
    return response.data;
  } catch (error) {
    console.log("Error resetting password:", error);
    throw error;
  }
}

const authService = {
  signIn,
  signUp,
  resetPassword,
};

export default authService;

// src/services/AuthService.ts
const API_URL = "http://localhost:9091/auth";
const AUTH_CHANGED_EVENT = 'authStateChanged';

export interface LoginResponse {
  token: string;
}

export interface UserProfile {
  id: string;
  username: string;
  email: string;
  role?: string;
  createdAt: string;
  lastLogin?: string;
}

export async function registerUser(email: string, username: string, password: string): Promise<string> {
  const res = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, username, password }),
  });
  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "Registration failed");
  }
  return res.text();
}

export async function loginUser(usernameOrEmail: string, password: string): Promise<string> {
  const res = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ usernameOrEmail, password }),
  });
  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "Login failed");
  }
  const data: LoginResponse = await res.json();
  localStorage.setItem("token", data.token);
  
  // Notify all components that auth state changed
  window.dispatchEvent(new Event(AUTH_CHANGED_EVENT));
  
  return data.token;
}

export async function getUserProfile(): Promise<UserProfile> {
  const token = getToken();
  if (!token) {
    throw new Error("No authentication token found");
  }
  const res = await fetch(`${API_URL}/profile`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
  if (!res.ok) {
    if (res.status === 401) {
      logout();
      throw new Error("Session expired. Please login again.");
    }
    throw new Error("Failed to fetch user profile");
  }
  return res.json();
}

export async function updateUserProfile(username: string, email: string): Promise<UserProfile> {
  const token = getToken();
  if (!token) {
    throw new Error("No authentication token found");
  }
  const res = await fetch(`${API_URL}/profile/update`, {
    method: "PUT",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, email }),
  });
  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "Failed to update profile");
  }
  
  const updatedProfile = await res.json();
  
  // Notify components that user data changed
  window.dispatchEvent(new Event(AUTH_CHANGED_EVENT));
  
  return updatedProfile;
}

export function logout(): void {
  localStorage.removeItem("token");
  
  // Notify all components that auth state changed
  window.dispatchEvent(new Event(AUTH_CHANGED_EVENT));
  
  window.location.href = "/login";
}

export function getToken(): string | null {
  return localStorage.getItem("token");
}

export function isLoggedIn(): boolean {
  const token = getToken();
  if (!token) return false;
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const expirationTime = payload.exp * 1000;
   
    if (Date.now() >= expirationTime) {
      logout();
      return false;
    }
   
    return true;
  } catch (error) {
    logout();
    return false;
  }
}

export function decodeToken(): any | null {
  const token = getToken();
  if (!token) return null;
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch (error) {
    return null;
  }
}

export function getUserIdFromToken(): string | null {
  const decoded = decodeToken();
  return decoded?.sub || decoded?.userId || null;
}

export function getUsernameFromToken(): string | null {
  const decoded = decodeToken();
  return decoded?.username || decoded?.name || null;
}
// src/hooks/useAuth.ts
import { useState, useEffect } from "react";
import { isLoggedIn, getToken, logout } from "../services/AuthService";

export function useAuth() {
  const [logged, setLogged] = useState<boolean>(isLoggedIn());

  useEffect(() => {
    setLogged(isLoggedIn());
  }, []);

  return { logged, token: getToken(), logout };
}

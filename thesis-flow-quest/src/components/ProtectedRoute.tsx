// src/components/ProtectedRoute.tsx (not in pages folder)
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { isLoggedIn } from "@/services/AuthService";

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export function ProtectedRoute({ children }: ProtectedRouteProps) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn()) {
      navigate("/login", { replace: true });
    }
  }, [navigate]);

  if (!isLoggedIn()) {
    return null;
  }

  return <>{children}</>;
}
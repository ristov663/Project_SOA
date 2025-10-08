// src/components/layout/Header.tsx
import { useState, useEffect } from "react";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { Button } from "@/components/ui/button";
import { Moon, Sun, Bell, LogOut } from "lucide-react";
import { useTheme } from "next-themes";
import { Badge } from "@/components/ui/badge";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { getUsernameFromToken, getUserIdFromToken, logout, isLoggedIn } from "@/services/AuthService";

export function Header() {
  const { theme, setTheme } = useTheme();
  const [username, setUsername] = useState<string | null>(null);
  const [userId, setUserId] = useState<string | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const checkAuth = () => {
    const loggedIn = isLoggedIn();
    setIsAuthenticated(loggedIn);
    
    if (loggedIn) {
      const name = getUsernameFromToken();
      const id = getUserIdFromToken();
      setUsername(name);
      setUserId(id);
    } else {
      setUsername(null);
      setUserId(null);
    }
  };

  useEffect(() => {
    checkAuth();
    
    // Listen for auth state changes
    const handleAuthChange = () => {
      checkAuth();
    };
    
    window.addEventListener('authStateChanged', handleAuthChange);
    
    return () => {
      window.removeEventListener('authStateChanged', handleAuthChange);
    };
  }, []);

  const toggleTheme = () => {
    setTheme(theme === "dark" ? "light" : "dark");
  };

  const handleLogout = () => {
    logout();
  };

  const getInitials = (name: string | null) => {
    if (!name) return "U";
    return name.charAt(0).toUpperCase();
  };

  return (
    <header className="h-16 border-b border-border bg-gradient-header shadow-soft">
      <div className="h-full flex items-center justify-between px-4">
        <div className="flex items-center gap-4">
          <SidebarTrigger className="text-primary-foreground hover:bg-primary-hover/20" />
          <div className="flex items-center gap-2">
            <h1 className="text-xl font-bold text-primary-foreground">
              Master Thesis Management System
            </h1>
          </div>
        </div>
        
        <div className="flex items-center gap-3">
          {isAuthenticated && (
            <Button 
              variant="ghost" 
              size="sm" 
              className="text-primary-foreground hover:bg-primary-hover/20 relative"
            >
              <Bell className="h-4 w-4" />
              <Badge
                variant="destructive"
                className="absolute -top-1 -right-1 h-5 w-5 flex items-center justify-center p-0 text-xs"
              >
                3
              </Badge>
            </Button>
          )}
          
          <Button
            variant="ghost"
            size="sm"
            onClick={toggleTheme}
            className="text-primary-foreground hover:bg-primary-hover/20"
          >
            {theme === "dark" ? <Sun className="h-4 w-4" /> : <Moon className="h-4 w-4" />}
          </Button>
          
          {isAuthenticated ? (
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button 
                  variant="ghost" 
                  className="text-primary-foreground hover:bg-primary-hover/20 flex items-center gap-2"
                >
                  <div className="w-8 h-8 rounded-full bg-primary-foreground/20 flex items-center justify-center">
                    <span className="text-sm font-medium">{getInitials(userId)}</span>
                  </div>
                  <span className="text-sm font-medium hidden md:inline">
                    {userId}
                  </span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end" className="w-56">
                <DropdownMenuLabel className="font-normal">
                  <div className="flex flex-col space-y-1">
                    <p className="text-sm font-medium leading-none">{username}</p>
                    {userId && (
                      <p className="text-xs leading-none text-muted-foreground">
                        ID: {userId.slice(0, 8)}...
                      </p>
                    )}
                  </div>
                </DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem onClick={handleLogout} className="text-red-600">
                  <LogOut className="mr-2 h-4 w-4" />
                  <span>Logout</span>
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          ) : (
            <Button
              variant="ghost"
              size="sm"
              onClick={() => window.location.href = "/login"}
              className="text-primary-foreground hover:bg-primary-hover/20"
            >
              Login
            </Button>
          )}
        </div>
      </div>
    </header>
  );
}
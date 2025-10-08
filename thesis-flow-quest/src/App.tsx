import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Layout } from "./components/layout/Layout";
import Dashboard from "./pages/Dashboard";
import Students from "./pages/Students";
import Professors from "./pages/Professors";
import Administrators from "./pages/Administrators";
import NotFound from "./pages/NotFound";
import Theses from "./pages/Theses";
import Login from "./pages/Login";
import Register from "./pages/Register";
import { ProtectedRoute } from "@/components/ProtectedRoute";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Layout>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            
            {/* Protected Routes - wrap the element, not the Route */}
            <Route 
              path="/students" 
              element={
                <ProtectedRoute>
                  <Students />
                </ProtectedRoute>
              } 
            />
            
            <Route 
              path="/professors" 
              element={
                <ProtectedRoute>
                  <Professors />
                </ProtectedRoute>
              } 
            />
            
            <Route 
              path="/administrators" 
              element={
                <ProtectedRoute>
                  <Administrators />
                </ProtectedRoute>
              } 
            />
            
            <Route 
              path="/theses" 
              element={
                <ProtectedRoute>
                  <Theses />
                </ProtectedRoute>
              } 
            />
            
            {/* Public routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            
            {/* Other routes */}
            <Route 
              path="/defenses" 
              element={
                <ProtectedRoute>
                  <div className="p-6">
                    <h1 className="text-2xl font-bold">Defenses</h1>
                    <p className="text-muted-foreground">Generate and view system reports.</p>
                  </div>
                </ProtectedRoute>
              } 
            />
            
            {/* Catch-all route must be last */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </Layout>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
import { 
  Users, 
  GraduationCap, 
  BookOpen, 
  Settings, 
  Home,
  FileText,
  Calendar,
  Bell,
  Archive
} from "lucide-react";
import { NavLink } from "react-router-dom";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";

const navigationItems = [
  {
    title: "Dashboard",
    url: "/",
    icon: Home,
    group: "main"
  },
  {
    title: "Students",
    url: "/students",
    icon: GraduationCap,
    group: "users"
  },
  {
    title: "Professors",
    url: "/professors",
    icon: Users,
    group: "users"
  },
  {
    title: "Administrators",
    url: "/administrators",
    icon: Settings,
    group: "users"
  },
  {
    title: "Theses",
    url: "/theses",
    icon: BookOpen,
    group: "thesis"
  },
  {
    title: "Defense Schedule",
    url: "/defenses",
    icon: Calendar,
    group: "thesis"
  },
  {
    title: "Archive",
    url: "/archive",
    icon: Archive,
    group: "thesis"
  },
  {
    title: "Notifications",
    url: "/notifications",
    icon: Bell,
    group: "system"
  },
  {
    title: "Reports",
    url: "/reports",
    icon: FileText,
    group: "system"
  },
];

const groupedItems = {
  main: navigationItems.filter(item => item.group === "main"),
  users: navigationItems.filter(item => item.group === "users"),
  thesis: navigationItems.filter(item => item.group === "thesis"),
  system: navigationItems.filter(item => item.group === "system"),
};

export function AppSidebar() {
  const { state } = useSidebar();
  const collapsed = state === "collapsed";

  const getNavClassName = ({ isActive }: { isActive: boolean }) =>
    isActive 
      ? "bg-sidebar-accent text-sidebar-accent-foreground font-medium" 
      : "hover:bg-sidebar-accent/50 hover:text-sidebar-accent-foreground";

  return (
    <Sidebar
      className={collapsed ? "w-14" : "w-64"}
      collapsible="icon"
    >
      <SidebarContent className="bg-sidebar">
        <div className="p-4 border-b border-sidebar-border">
          <h2 className={`font-bold text-sidebar-foreground ${collapsed ? "text-xs text-center" : "text-lg"}`}>
            {collapsed ? "TMS" : "Thesis Management"}
          </h2>
        </div>

        <SidebarGroup>
          <SidebarGroupLabel className="text-sidebar-foreground/80">
            {!collapsed && "Main"}
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {groupedItems.main.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <NavLink to={item.url} end className={getNavClassName}>
                      <item.icon className="h-4 w-4" />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        <SidebarGroup>
          <SidebarGroupLabel className="text-sidebar-foreground/80">
            {!collapsed && "User Management"}
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {groupedItems.users.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <NavLink to={item.url} className={getNavClassName}>
                      <item.icon className="h-4 w-4" />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        <SidebarGroup>
          <SidebarGroupLabel className="text-sidebar-foreground/80">
            {!collapsed && "Thesis Management"}
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {groupedItems.thesis.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <NavLink to={item.url} className={getNavClassName}>
                      <item.icon className="h-4 w-4" />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        <SidebarGroup>
          <SidebarGroupLabel className="text-sidebar-foreground/80">
            {!collapsed && "System"}
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {groupedItems.system.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <NavLink to={item.url} className={getNavClassName}>
                      <item.icon className="h-4 w-4" />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { 
  Users, 
  BookOpen, 
  Calendar, 
  TrendingUp,
  GraduationCap,
  UserCheck,
  Clock,
  CheckCircle,
  AlertCircle,
  Archive
} from "lucide-react";
import { Link } from "react-router-dom";

// Mock data - replace with real data from your API
const dashboardData = {
  stats: {
    totalStudents: 245,
    totalProfessors: 45,
    activeTheses: 156,
    completedTheses: 89,
    upcomingDefenses: 12,
    overdueDeadlines: 8,
  },
  recentTheses: [
    { id: "1", title: "Machine Learning in Healthcare", student: "John Doe", status: "UNDER_REVIEW", mentor: "Prof. Smith" },
    { id: "2", title: "Blockchain Applications", student: "Jane Smith", status: "IN_PROGRESS", mentor: "Prof. Johnson" },
    { id: "3", title: "AI Ethics Framework", student: "Mike Wilson", status: "SUBMITTED", mentor: "Prof. Brown" },
  ],
  upcomingDefenses: [
    { id: "1", title: "Deep Learning Analysis", student: "Alice Cooper", date: "2024-01-25", time: "10:00 AM" },
    { id: "2", title: "Cloud Computing Security", student: "Bob Johnson", date: "2024-01-26", time: "2:00 PM" },
  ],
};

const statusColors = {
  PROPOSED: "secondary",
  APPROVED: "default",
  IN_PROGRESS: "default",
  SUBMITTED: "secondary",
  UNDER_REVIEW: "warning",
  DEFENDED: "success",
  ARCHIVED: "secondary",
  REJECTED: "destructive",
} as const;

export default function Dashboard() {
  const { stats, recentTheses, upcomingDefenses } = dashboardData;

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold text-foreground">Dashboard</h1>
        <p className="text-muted-foreground mt-1">Overview of thesis management activities</p>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card className="bg-gradient-to-br from-primary/5 to-primary/10 border-primary/20">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Students</CardTitle>
            <GraduationCap className="h-4 w-4 text-primary" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-primary">{stats.totalStudents}</div>
            <p className="text-xs text-muted-foreground">+12% from last month</p>
          </CardContent>
        </Card>

        <Card className="bg-gradient-to-br from-success/5 to-success/10 border-success/20">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Professors</CardTitle>
            <UserCheck className="h-4 w-4 text-success" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-success">{stats.totalProfessors}</div>
            <p className="text-xs text-muted-foreground">Available mentors</p>
          </CardContent>
        </Card>

        <Card className="bg-gradient-to-br from-warning/5 to-warning/10 border-warning/20">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Active Theses</CardTitle>
            <BookOpen className="h-4 w-4 text-warning" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-warning">{stats.activeTheses}</div>
            <p className="text-xs text-muted-foreground">In progress</p>
          </CardContent>
        </Card>

        <Card className="bg-gradient-to-br from-destructive/5 to-destructive/10 border-destructive/20">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Overdue</CardTitle>
            <AlertCircle className="h-4 w-4 text-destructive" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-destructive">{stats.overdueDeadlines}</div>
            <p className="text-xs text-muted-foreground">Require attention</p>
          </CardContent>
        </Card>
      </div>

      {/* Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Theses */}
        <Card>
          <CardHeader>
            <div className="flex items-center justify-between">
              <div>
                <CardTitle className="flex items-center gap-2">
                  <BookOpen className="h-5 w-5" />
                  Recent Theses
                </CardTitle>
                <CardDescription>Latest thesis submissions and updates</CardDescription>
              </div>
              <Button variant="outline" size="sm" asChild>
                <Link to="/theses">View All</Link>
              </Button>
            </div>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {recentTheses.map((thesis) => (
                <div key={thesis.id} className="flex items-center justify-between p-3 rounded-lg border border-border hover:bg-muted/50 transition-colors">
                  <div className="flex-1">
                    <h4 className="font-medium text-sm">{thesis.title}</h4>
                    <p className="text-xs text-muted-foreground">
                      by {thesis.student} • {thesis.mentor}
                    </p>
                  </div>
                  <Badge variant={statusColors[thesis.status as keyof typeof statusColors]}>
                    {thesis.status.replace('_', ' ')}
                  </Badge>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        {/* Upcoming Defenses */}
        <Card>
          <CardHeader>
            <div className="flex items-center justify-between">
              <div>
                <CardTitle className="flex items-center gap-2">
                  <Calendar className="h-5 w-5" />
                  Upcoming Defenses
                </CardTitle>
                <CardDescription>Scheduled thesis defenses</CardDescription>
              </div>
              <Button variant="outline" size="sm" asChild>
                <Link to="/defenses">View All</Link>
              </Button>
            </div>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {upcomingDefenses.map((defense) => (
                <div key={defense.id} className="flex items-center justify-between p-3 rounded-lg border border-border hover:bg-muted/50 transition-colors">
                  <div className="flex-1">
                    <h4 className="font-medium text-sm">{defense.title}</h4>
                    <p className="text-xs text-muted-foreground">
                      by {defense.student}
                    </p>
                  </div>
                  <div className="text-right">
                    <div className="text-sm font-medium">{defense.date}</div>
                    <div className="text-xs text-muted-foreground">{defense.time}</div>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Quick Actions */}
      <Card>
        <CardHeader>
          <CardTitle>Quick Actions</CardTitle>
          <CardDescription>Common tasks and shortcuts</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <Button variant="outline" className="h-auto p-4 flex flex-col gap-2" asChild>
              <Link to="/students/new">
                <GraduationCap className="h-8 w-8" />
                <span>Add Student</span>
              </Link>
            </Button>
            <Button variant="outline" className="h-auto p-4 flex flex-col gap-2" asChild>
              <Link to="/professors/new">
                <Users className="h-8 w-8" />
                <span>Add Professor</span>
              </Link>
            </Button>
            <Button variant="outline" className="h-auto p-4 flex flex-col gap-2" asChild>
              <Link to="/theses/new">
                <BookOpen className="h-8 w-8" />
                <span>New Thesis</span>
              </Link>
            </Button>
            <Button variant="outline" className="h-auto p-4 flex flex-col gap-2" asChild>
              <Link to="/reports">
                <TrendingUp className="h-8 w-8" />
                <span>View Reports</span>
              </Link>
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
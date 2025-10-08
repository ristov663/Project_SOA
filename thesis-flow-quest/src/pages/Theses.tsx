import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Plus, MoreHorizontal, Pencil, Trash, Eye, Calendar, FileText, Search, Filter, Archive, Shield, User, GraduationCap, AlertCircle, CheckCircle, RefreshCw, Database } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Alert, AlertDescription } from "@/components/ui/alert";

// Global thesis data store - shared across all components
if (!window.globalThesisStore) {
  window.globalThesisStore = {
    data: {
      studentSubmissions: [],
      defendedTheses: [],
      scheduledDefenses: [],
      validatedTheses: [],
      archivedTheses: [],
      commissionSelections: [],
      adminValidations: [],
      commissionValidations: [],
      secretaryValidations: []
    },
    listeners: new Set(),

    // Subscribe to changes
    subscribe(callback) {
      this.listeners.add(callback);
      return () => this.listeners.delete(callback);
    },

    // Notify all listeners
    notify() {
      this.listeners.forEach(callback => callback(this.data));
    },

    // Add data methods
    addStudentSubmission(submission) {
      this.data.studentSubmissions.push({
        ...submission,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addDefendedThesis(defense) {
      this.data.defendedTheses.push({
        ...defense,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addScheduledDefense(schedule) {
      this.data.scheduledDefenses.push({
        ...schedule,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addValidatedThesis(validation) {
      this.data.validatedTheses.push({
        ...validation,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addArchivedThesis(archive) {
      this.data.archivedTheses.push({
        ...archive,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addCommissionSelection(commission) {
      this.data.commissionSelections.push({
        ...commission,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addAdminValidation(adminVal) {
      this.data.adminValidations.push({
        ...adminVal,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addCommissionValidation(commVal) {
      this.data.commissionValidations.push({
        ...commVal,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    addSecretaryValidation(secVal) {
      this.data.secretaryValidations.push({
        ...secVal,
        timestamp: new Date().toLocaleString(),
        id: Date.now()
      });
      this.notify();
    },

    // Get all data
    getAllData() {
      return this.data;
    }
  };
}

// Custom hook to use the global store
const useGlobalThesisStore = () => {
  const [data, setData] = useState(window.globalThesisStore.getAllData());

  useEffect(() => {
    const unsubscribe = window.globalThesisStore.subscribe((newData) => {
      setData({ ...newData });
    });

    return unsubscribe;
  }, []);

  return {
    data,
    addStudentSubmission: (submission) => window.globalThesisStore.addStudentSubmission(submission),
    addDefendedThesis: (defense) => window.globalThesisStore.addDefendedThesis(defense),
    addScheduledDefense: (schedule) => window.globalThesisStore.addScheduledDefense(schedule),
    addValidatedThesis: (validation) => window.globalThesisStore.addValidatedThesis(validation),
    addArchivedThesis: (archive) => window.globalThesisStore.addArchivedThesis(archive),
    addCommissionSelection: (commission) => window.globalThesisStore.addCommissionSelection(commission),
    addAdminValidation: (adminVal) => window.globalThesisStore.addAdminValidation(adminVal),
    addCommissionValidation: (commVal) => window.globalThesisStore.addCommissionValidation(commVal),
    addSecretaryValidation: (secVal) => window.globalThesisStore.addSecretaryValidation(secVal)
  };
};

// Comprehensive thesis data structure
interface ComprehensiveThesis {
  thesisId: string;
  title?: string;
  studentId?: string;
  mentorId?: string;
  shortDescription?: string;
  currentStatus: string;
  currentPhase: string;
  submissionData?: any;
  defenseData?: any;
  scheduledDefenseData?: any;
  validationData?: any;
  archiveData?: any;
  commissionData?: any;
  adminValidationData?: any;
  lastActivity: string;
  statusColor: 'default' | 'secondary' | 'destructive' | 'warning' | 'success';
}

export default function Theses() {
  const { data } = useGlobalThesisStore();
  
  const [comprehensiveTheses, setComprehensiveTheses] = useState<ComprehensiveThesis[]>([]);
  const [filteredTheses, setFilteredTheses] = useState<ComprehensiveThesis[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('all');
  const [lastUpdated, setLastUpdated] = useState<Date | null>(null);

  // Function to determine thesis status and phase
  const determineThesisStatus = (
    thesisId: string,
    submissions: any[],
    defended: any[],
    scheduled: any[],
    validated: any[],
    archived: any[],
    commissions: any[],
    adminValidated: any[],
    commissionValidated: any[],
    secretaryValidated: any[]
  ): { status: string; phase: string; color: 'default' | 'secondary' | 'destructive' | 'warning' | 'success' } => {
    
    if (archived.some(a => a.thesisId === thesisId)) {
      return { status: 'Archived', phase: 'Completed', color: 'secondary' };
    }
    
    if (defended.some(d => d.thesisId === thesisId)) {
      const defense = defended.find(d => d.thesisId === thesisId);
      if (defense?.successful) {
        return { status: 'Successfully Defended', phase: 'Defense Completed', color: 'success' };
      } else {
        return { status: 'Defense Failed', phase: 'Defense Phase', color: 'destructive' };
      }
    }
    
    if (scheduled.some(s => s.thesisId === thesisId)) {
      return { status: 'Defense Scheduled', phase: 'Pre-Defense', color: 'warning' };
    }
    
    if (commissions.some(c => c.thesisId === thesisId)) {
      return { status: 'Commission Selected', phase: 'Pre-Defense', color: 'default' };
    }
    
    if (validated.some(v => v.thesisId === thesisId)) {
      const validation = validated.find(v => v.thesisId === thesisId);
      if (validation?.approved) {
        return { status: 'Mentor Approved', phase: 'Validation Phase', color: 'default' };
      } else {
        return { status: 'Mentor Rejected', phase: 'Validation Phase', color: 'destructive' };
      }
    }

    if (secretaryValidated.some(s => s.thesisId === thesisId)) {
      return { status: 'Secretary Validated', phase: 'Administrative Review', color: 'default' };
    }

    if (commissionValidated.some(c => c.thesisId === thesisId)) {
      const commVal = commissionValidated.find(c => c.thesisId === thesisId);
      if (commVal?.approved) {
        return { status: 'Commission Approved', phase: 'Administrative Review', color: 'default' };
      } else {
        return { status: 'Commission Rejected', phase: 'Administrative Review', color: 'destructive' };
      }
    }
    
    if (adminValidated.some(a => a.thesisId === thesisId)) {
      const adminVal = adminValidated.find(a => a.thesisId === thesisId);
      if (adminVal?.approved) {
        return { status: 'Admin Approved', phase: 'Administrative Review', color: 'default' };
      } else {
        return { status: 'Admin Rejected', phase: 'Administrative Review', color: 'destructive' };
      }
    }
    
    if (submissions.some(s => s.thesisId === thesisId)) {
      return { status: 'Submitted', phase: 'Initial Review', color: 'secondary' };
    }
    
    return { status: 'Unknown', phase: 'Unknown', color: 'secondary' };
  };

  // Consolidate thesis data whenever data changes
  useEffect(() => {
    const {
      studentSubmissions,
      defendedTheses,
      scheduledDefenses,
      validatedTheses,
      archivedTheses,
      commissionSelections,
      adminValidations,
      commissionValidations,
      secretaryValidations
    } = data;

    const thesisMap = new Map<string, ComprehensiveThesis>();
    
    // Collect all unique thesis IDs from all sources
    const allThesisIds = new Set<string>();
    
    [...studentSubmissions, ...defendedTheses, ...scheduledDefenses, ...validatedTheses, 
     ...archivedTheses, ...commissionSelections, ...adminValidations, ...commissionValidations, 
     ...secretaryValidations].forEach(item => {
      if (item.thesisId) {
        allThesisIds.add(item.thesisId);
      }
    });

    // Create comprehensive thesis records
    allThesisIds.forEach(thesisId => {
      const statusInfo = determineThesisStatus(
        thesisId,
        studentSubmissions,
        defendedTheses,
        scheduledDefenses,
        validatedTheses,
        archivedTheses,
        commissionSelections,
        adminValidations,
        commissionValidations,
        secretaryValidations
      );

      // Find submission data for this thesis
      const submission = studentSubmissions.find(s => s.thesisId === thesisId);

      // Find latest activity timestamp
      const allRecords = [
        ...studentSubmissions.filter(s => s.thesisId === thesisId),
        ...defendedTheses.filter(d => d.thesisId === thesisId),
        ...scheduledDefenses.filter(s => s.thesisId === thesisId),
        ...validatedTheses.filter(v => v.thesisId === thesisId),
        ...archivedTheses.filter(a => a.thesisId === thesisId),
        ...commissionSelections.filter(c => c.thesisId === thesisId),
        ...adminValidations.filter(av => av.thesisId === thesisId),
        ...commissionValidations.filter(cv => cv.thesisId === thesisId),
        ...secretaryValidations.filter(sv => sv.thesisId === thesisId)
      ];

      const latestActivity = allRecords.reduce((latest, record) => {
        const timestamp = record.timestamp || record.submissionDate || record.defenseDate || record.validationDate || new Date().toISOString();
        return new Date(timestamp) > new Date(latest) ? timestamp : latest;
      }, new Date(0).toISOString());

      const comprehensiveThesis: ComprehensiveThesis = {
        thesisId,
        title: submission?.title,
        studentId: submission?.studentId,
        mentorId: submission?.mentorId,
        shortDescription: submission?.shortDescription,
        currentStatus: statusInfo.status,
        currentPhase: statusInfo.phase,
        statusColor: statusInfo.color,
        lastActivity: latestActivity,
        submissionData: submission,
        defenseData: defendedTheses.find(d => d.thesisId === thesisId),
        scheduledDefenseData: scheduledDefenses.find(s => s.thesisId === thesisId),
        validationData: validatedTheses.find(v => v.thesisId === thesisId),
        archiveData: archivedTheses.find(a => a.thesisId === thesisId),
        commissionData: commissionSelections.find(c => c.thesisId === thesisId),
        adminValidationData: adminValidations.find(av => av.thesisId === thesisId)
      };

      thesisMap.set(thesisId, comprehensiveThesis);
    });

    const consolidated = Array.from(thesisMap.values())
      .sort((a, b) => new Date(b.lastActivity).getTime() - new Date(a.lastActivity).getTime());
    
    setComprehensiveTheses(consolidated);
    setFilteredTheses(consolidated);
    setLastUpdated(new Date());
  }, [data]);

  // Filter and search functionality
  useEffect(() => {
    let filtered = comprehensiveTheses;
    
    if (searchTerm) {
      filtered = filtered.filter(thesis =>
        thesis.title?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        thesis.thesisId.toLowerCase().includes(searchTerm.toLowerCase()) ||
        thesis.studentId?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        thesis.mentorId?.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
    
    if (statusFilter !== 'all') {
      filtered = filtered.filter(thesis =>
        thesis.currentStatus.toLowerCase().includes(statusFilter.toLowerCase()) ||
        thesis.currentPhase.toLowerCase().includes(statusFilter.toLowerCase())
      );
    }
    
    setFilteredTheses(filtered);
  }, [searchTerm, statusFilter, comprehensiveTheses]);

  const statusCounts = comprehensiveTheses.reduce((acc, thesis) => {
    acc[thesis.currentStatus] = (acc[thesis.currentStatus] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);

  // Table component
  const DataTable = ({ data, columns, title }: any) => (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h3 className="text-lg font-medium">{title}</h3>
        <Badge variant="secondary">{data.length} theses</Badge>
      </div>
      
      <div className="border rounded-md">
        <div className="overflow-x-auto">
          <table className="w-full border-collapse">
            <thead>
              <tr className="border-b bg-muted/50">
                {columns.map((col: any, idx: number) => (
                  <th key={idx} className="px-4 py-3 text-left font-medium text-sm">
                    {col.header}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {data.length === 0 ? (
                <tr>
                  <td colSpan={columns.length} className="px-4 py-8 text-center text-muted-foreground">
                    No theses found. Add some data in Student Submission, Mentor Dashboard, or Administration pages.
                  </td>
                </tr>
              ) : (
                data.map((row: ComprehensiveThesis, idx: number) => (
                  <tr key={idx} className={idx % 2 === 0 ? "bg-background" : "bg-muted/25"}>
                    {columns.map((col: any, colIdx: number) => (
                      <td key={colIdx} className="px-4 py-3 text-sm">
                        {col.render ? col.render(row) : row[col.key as keyof ComprehensiveThesis]}
                      </td>
                    ))}
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );

  // Table columns
  const comprehensiveColumns = [
    {
      key: 'thesisId',
      header: 'Thesis ID',
      render: (thesis: ComprehensiveThesis) => (
        <div className="font-medium">{thesis.thesisId}</div>
      )
    },
    {
      key: 'title',
      header: 'Title',
      render: (thesis: ComprehensiveThesis) => (
        <div className="max-w-md">
          <div className="font-medium truncate">{thesis.title || 'N/A'}</div>
          <div className="text-xs text-muted-foreground mt-1">
            {thesis.shortDescription && thesis.shortDescription.length > 50 
              ? `${thesis.shortDescription.substring(0, 50)}...`
              : thesis.shortDescription || 'No description'
            }
          </div>
        </div>
      )
    },
    {
      key: 'currentStatus',
      header: 'Status',
      render: (thesis: ComprehensiveThesis) => (
        <Badge variant={thesis.statusColor}>{thesis.currentStatus}</Badge>
      )
    },
    {
      key: 'currentPhase',
      header: 'Phase',
      render: (thesis: ComprehensiveThesis) => (
        <div className="text-sm">{thesis.currentPhase}</div>
      )
    },
    {
      key: 'studentId',
      header: 'Student',
      render: (thesis: ComprehensiveThesis) => (
        <div className="text-sm">{thesis.studentId || 'N/A'}</div>
      )
    },
    {
      key: 'mentorId',
      header: 'Mentor',
      render: (thesis: ComprehensiveThesis) => (
        <div className="text-sm">{thesis.mentorId || 'N/A'}</div>
      )
    },
    {
      key: 'lastActivity',
      header: 'Last Activity',
      render: (thesis: ComprehensiveThesis) => (
        <div className="text-xs text-muted-foreground">
          {new Date(thesis.lastActivity).toLocaleDateString()}
        </div>
      )
    },
    {
      key: 'actions',
      header: 'Actions',
      render: (thesis: ComprehensiveThesis) => (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem>
              <Eye className="mr-2 h-4 w-4" />
              View Details
            </DropdownMenuItem>
            <DropdownMenuItem>
              <Pencil className="mr-2 h-4 w-4" />
              Edit
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem>
              <Calendar className="mr-2 h-4 w-4" />
              Schedule Defense
            </DropdownMenuItem>
            <DropdownMenuItem>
              <FileText className="mr-2 h-4 w-4" />
              Generate Report
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      )
    }
  ];

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-foreground">Thesis Management</h1>
          <p className="text-muted-foreground mt-1">
            Live view of all thesis data from student, mentor, and admin activities
            {lastUpdated && (
              <span className="block text-xs mt-1">
                Last updated: {lastUpdated.toLocaleString()}
              </span>
            )}
          </p>
        </div>
        <Button>
          <Plus className="mr-2 h-4 w-4" />
          New Thesis
        </Button>
      </div>

      {/* Live Data Indicator */}
      <Alert>
        <Database className="h-4 w-4" />
        <AlertDescription>
          This page automatically updates when you add data in Student Submission, Mentor Dashboard, or Administration pages.
          Currently showing {comprehensiveTheses.length} thesis records from live data.
        </AlertDescription>
      </Alert>

      {/* Stats Overview */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Theses</CardTitle>
            <FileText className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{comprehensiveTheses.length}</div>
            <p className="text-xs text-muted-foreground">
              Live from user actions
            </p>
          </CardContent>
        </Card>
        
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">In Progress</CardTitle>
            <Calendar className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-warning">
              {(statusCounts['Submitted'] || 0) + 
               (statusCounts['Under Review'] || 0) + 
               (statusCounts['Admin Approved'] || 0) + 
               (statusCounts['Mentor Approved'] || 0)}
            </div>
          </CardContent>
        </Card>
        
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Defended</CardTitle>
            <GraduationCap className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-success">
              {statusCounts['Successfully Defended'] || 0}
            </div>
          </CardContent>
        </Card>
        
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Archived</CardTitle>
            <Archive className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-muted-foreground">
              {statusCounts['Archived'] || 0}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Search and Filters */}
      <Card>
        <CardContent className="pt-6">
          <div className="flex flex-col md:flex-row gap-4">
            <div className="flex-1">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
                <Input
                  placeholder="Search by thesis ID, title, student, or mentor..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10"
                />
              </div>
            </div>
            <div className="w-full md:w-48">
              <select
                value={statusFilter}
                onChange={(e) => setStatusFilter(e.target.value)}
                className="w-full p-2 border rounded-md"
              >
                <option value="all">All Statuses</option>
                <option value="submitted">Submitted</option>
                <option value="approved">Approved</option>
                <option value="scheduled">Defense Scheduled</option>
                <option value="defended">Defended</option>
                <option value="archived">Archived</option>
              </select>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Main Data Display */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <FileText className="h-5 w-5" />
            Live Thesis Overview
          </CardTitle>
          <CardDescription>
            Real-time updates from all thesis management activities across the system
          </CardDescription>
        </CardHeader>
        <CardContent>
          <DataTable 
            data={filteredTheses} 
            columns={comprehensiveColumns} 
            title="All Thesis Records" 
          />
        </CardContent>
      </Card>

      {/* Data Source Breakdown */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <User className="h-4 w-4" />
              Student Submissions
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold mb-2">{data.studentSubmissions.length}</div>
            <div className="text-sm text-muted-foreground">
              Thesis registrations submitted by students
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <GraduationCap className="h-4 w-4" />
              Mentor Activities
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-1 text-sm">
              <div>Defended: {data.defendedTheses.length}</div>
              <div>Scheduled: {data.scheduledDefenses.length}</div>
              <div>Validated: {data.validatedTheses.length}</div>
              <div>Commission Selected: {data.commissionSelections.length}</div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Shield className="h-4 w-4" />
              Administration
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-1 text-sm">
              <div>Admin Validations: {data.adminValidations.length}</div>
              <div>Commission Validations: {data.commissionValidations.length}</div>
              <div>Secretary Validations: {data.secretaryValidations.length}</div>
              <div>Archived: {data.archivedTheses.length}</div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
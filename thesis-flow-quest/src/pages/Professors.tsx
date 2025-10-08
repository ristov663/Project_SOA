import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { FileText, Shield, User, Calendar, AlertCircle, CheckCircle, Table, Upload, Users, ClipboardCheck, GraduationCap } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";

const BACKEND_URL = "http://localhost:9091";

// Types based on your DTOs
interface DocumentInfo {
  documentId: string;
  fileName: string;
  fileSize: number;
  mimeType: string;
  uploadDate: string;
  uploadedBy: { value: string };
  documentType: string;
}

// API service functions
const mentorThesisService = {
  async markThesisAsDefended(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/mark-thesis`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async scheduleThesisDefense(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/schedule-defense`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async selectCommissionMembers(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/select-commission-members`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async submitCommissionReport(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/submit-commission-report`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async uploadRevisedThesisDraft(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/upload-revised-thesis`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async uploadThesisDraft(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/upload-thesis`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateThesisByMentor(data: any) {
    const response = await fetch(`${BACKEND_URL}/mentor/master-thesis/validate-by-mentor`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
};

export default function MentorDashboard() {
  const [activeAction, setActiveAction] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  // Tables data state
  const [defendedTheses, setDefendedTheses] = useState<any[]>([]);
  const [scheduledDefenses, setScheduledDefenses] = useState<any[]>([]);
  const [selectedCommissions, setSelectedCommissions] = useState<any[]>([]);
  const [submittedReports, setSubmittedReports] = useState<any[]>([]);
  const [uploadedDrafts, setUploadedDrafts] = useState<any[]>([]);
  const [revisedDrafts, setRevisedDrafts] = useState<any[]>([]);
  const [validatedTheses, setValidatedTheses] = useState<any[]>([]);

  const clearMessages = () => {
    setSuccess(null);
    setError(null);
  };

  // Mapping functions for backend payloads
  const mapDefendedPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    defenseDate: formData.defenseDate,
    successful: formData.successful,
    finalGrade: { value: formData.finalGrade },
    defenseRemarks: formData.defenseRemarks?.trim() || null,
  });

  const mapScheduleDefensePayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    mentorId: { value: formData.mentorId },
    defenseDate: formData.defenseDate,
    roomId: formData.roomId,
    remarks: formData.remarks?.trim() || null,
    schedulingDate: formData.schedulingDate,
  });

  const mapCommissionMembersPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    mentorId: { value: formData.mentorId },
    commissionMember1Id: { value: formData.commissionMember1Id },
    commissionMember2Id: { value: formData.commissionMember2Id },
    remarks: formData.remarks?.trim() || null,
    selectionDate: formData.selectionDate,
  });

  const mapCommissionReportPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    mentorId: { value: formData.mentorId },
    reportDocument: formData.reportDocument,
    corrections: formData.corrections?.trim() || null,
    remarks: formData.remarks?.trim() || null,
    conclusions: formData.conclusions?.trim() || null,
    submissionDate: formData.submissionDate,
  });

  const mapUploadDraftPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    mentorId: { value: formData.mentorId },
    draftDocumentType: formData.draftDocumentType,
    remarks: formData.remarks?.trim() || null,
    uploadDate: formData.uploadDate,
  });

  const mapValidatePayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    mentorId: { value: formData.mentorId },
    approved: formData.approved,
    remarks: formData.remarks?.trim() || null,
    field: formData.field ? { value: formData.field } : null,
    validationDate: formData.validationDate,
  });

  const handleSubmit = async (action: string, formData: any) => {
    setLoading(true);
    clearMessages();

    try {
      let payload;
      let tableData;
      
      switch (action) {
        case 'markDefended':
          payload = mapDefendedPayload(formData);
          await mentorThesisService.markThesisAsDefended(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setDefendedTheses(prev => [...prev, tableData]);
          setSuccess('Thesis marked as defended successfully');
          break;
        case 'scheduleDefense':
          payload = mapScheduleDefensePayload(formData);
          await mentorThesisService.scheduleThesisDefense(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setScheduledDefenses(prev => [...prev, tableData]);
          setSuccess('Thesis defense scheduled successfully');
          break;
        case 'selectCommission':
          payload = mapCommissionMembersPayload(formData);
          await mentorThesisService.selectCommissionMembers(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setSelectedCommissions(prev => [...prev, tableData]);
          setSuccess('Commission members selected successfully');
          break;
        case 'submitReport':
          payload = mapCommissionReportPayload(formData);
          await mentorThesisService.submitCommissionReport(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setSubmittedReports(prev => [...prev, tableData]);
          setSuccess('Commission report submitted successfully');
          break;
        case 'uploadDraft':
          payload = mapUploadDraftPayload(formData);
          await mentorThesisService.uploadThesisDraft(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setUploadedDrafts(prev => [...prev, tableData]);
          setSuccess('Thesis draft uploaded successfully');
          break;
        case 'uploadRevised':
          payload = mapUploadDraftPayload(formData); // Same structure as upload draft
          await mentorThesisService.uploadRevisedThesisDraft(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setRevisedDrafts(prev => [...prev, tableData]);
          setSuccess('Revised thesis draft uploaded successfully');
          break;
        case 'validate':
          payload = mapValidatePayload(formData);
          await mentorThesisService.validateThesisByMentor(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setValidatedTheses(prev => [...prev, tableData]);
          setSuccess('Thesis validated by mentor successfully');
          break;
        default:
          throw new Error('Unknown action');
      }
      setActiveAction(null);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  const ActionCard = ({ title, description, icon, action, children }: any) => (
    <Card className={activeAction === action ? "border-primary" : ""}>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">{icon}{title}</CardTitle>
        <CardDescription>{description}</CardDescription>
      </CardHeader>
      <CardContent>
        {activeAction === action ? (
          <div className="space-y-4">
            {children}
            <div className="flex gap-2">
              <Button onClick={() => setActiveAction(null)} variant="outline" disabled={loading}>Cancel</Button>
            </div>
          </div>
        ) : (
          <Button onClick={() => { setActiveAction(action); clearMessages(); }}>Start {title}</Button>
        )}
      </CardContent>
    </Card>
  );

  // Table component
  const DataTable = ({ data, columns, title }: any) => (
    <Card className="mt-6">
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Table className="h-5 w-5" />
          {title}
        </CardTitle>
      </CardHeader>
      <CardContent>
        {data.length === 0 ? (
          <p className="text-muted-foreground text-center py-4">No records yet</p>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse border border-gray-300">
              <thead>
                <tr className="bg-gray-50">
                  {columns.map((col: any, idx: number) => (
                    <th key={idx} className="border border-gray-300 px-4 py-2 text-left font-medium">
                      {col.header}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {data.map((row: any, idx: number) => (
                  <tr key={idx} className={idx % 2 === 0 ? "bg-white" : "bg-gray-50"}>
                    {columns.map((col: any, colIdx: number) => (
                      <td key={colIdx} className="border border-gray-300 px-4 py-2">
                        {col.render ? col.render(row[col.key]) : row[col.key]}
                      </td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </CardContent>
    </Card>
  );

  // Forms
  const MarkDefendedForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', defenseDate: '', successful: false, finalGrade: '', defenseRemarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="defenseDate">Defense Date</Label><Input id="defenseDate" type="datetime-local" value={formData.defenseDate} onChange={(e) => setFormData({...formData, defenseDate: e.target.value})}/></div>
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div className="flex items-center space-x-2"><Checkbox id="successful" checked={formData.successful} onCheckedChange={(checked) => setFormData({...formData, successful: !!checked})}/><Label htmlFor="successful">Defense Successful</Label></div>
          <div><Label htmlFor="finalGrade">Final Grade</Label><Input id="finalGrade" value={formData.finalGrade} onChange={(e) => setFormData({...formData, finalGrade: e.target.value})} placeholder="Enter final grade"/></div>
        </div>
        <div><Label htmlFor="defenseRemarks">Defense Remarks</Label><Textarea id="defenseRemarks" value={formData.defenseRemarks} onChange={(e) => setFormData({...formData, defenseRemarks: e.target.value})} placeholder="Enter defense remarks"/></div>
        <Button onClick={() => handleSubmit('markDefended', formData)} disabled={loading || !formData.thesisId || !formData.defenseDate} className="w-full">{loading ? 'Marking...' : 'Mark as Defended'}</Button>
      </>
    );
  };

  const ScheduleDefenseForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', mentorId: '', defenseDate: '', roomId: '', remarks: '', schedulingDate: new Date().toISOString() });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="mentorId">Mentor ID</Label><Input id="mentorId" value={formData.mentorId} onChange={(e) => setFormData({...formData, mentorId: e.target.value})} placeholder="Enter mentor ID"/></div>
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="defenseDate">Defense Date</Label><Input id="defenseDate" type="datetime-local" value={formData.defenseDate} onChange={(e) => setFormData({...formData, defenseDate: e.target.value})}/></div>
          <div><Label htmlFor="roomId">Room ID</Label><Input id="roomId" value={formData.roomId} onChange={(e) => setFormData({...formData, roomId: e.target.value})} placeholder="Enter room ID"/></div>
        </div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('scheduleDefense', formData)} disabled={loading || !formData.thesisId || !formData.mentorId || !formData.defenseDate || !formData.roomId} className="w-full">{loading ? 'Scheduling...' : 'Schedule Defense'}</Button>
      </>
    );
  };

  const SelectCommissionForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', mentorId: '', commissionMember1Id: '', commissionMember2Id: '', remarks: '', selectionDate: new Date().toISOString() });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="mentorId">Mentor ID</Label><Input id="mentorId" value={formData.mentorId} onChange={(e) => setFormData({...formData, mentorId: e.target.value})} placeholder="Enter mentor ID"/></div>
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="commissionMember1Id">Commission Member 1 ID</Label><Input id="commissionMember1Id" value={formData.commissionMember1Id} onChange={(e) => setFormData({...formData, commissionMember1Id: e.target.value})} placeholder="Enter member 1 ID"/></div>
          <div><Label htmlFor="commissionMember2Id">Commission Member 2 ID</Label><Input id="commissionMember2Id" value={formData.commissionMember2Id} onChange={(e) => setFormData({...formData, commissionMember2Id: e.target.value})} placeholder="Enter member 2 ID"/></div>
        </div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('selectCommission', formData)} disabled={loading || !formData.thesisId || !formData.mentorId || !formData.commissionMember1Id || !formData.commissionMember2Id} className="w-full">{loading ? 'Selecting...' : 'Select Commission Members'}</Button>
      </>
    );
  };

  const ValidateThesisForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', mentorId: '', approved: false, remarks: '', field: '', validationDate: new Date().toISOString() });
    
    const thesisAreas = [
      'COMPUTER_SCIENCE',
      'SOFTWARE_ENGINEERING',
      'ARTIFICIAL_INTELLIGENCE',
      'DATA_SCIENCE',
      'CYBERSECURITY',
      'HUMAN_COMPUTER_INTERACTION',
      'COMPUTER_NETWORKS',
      'DATABASE_SYSTEMS'
    ];

    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="mentorId">Mentor ID</Label><Input id="mentorId" value={formData.mentorId} onChange={(e) => setFormData({...formData, mentorId: e.target.value})} placeholder="Enter mentor ID"/></div>
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div className="flex items-center space-x-2"><Checkbox id="approved" checked={formData.approved} onCheckedChange={(checked) => setFormData({...formData, approved: !!checked})}/><Label htmlFor="approved">Approved</Label></div>
          <div>
            <Label htmlFor="field">Thesis Area</Label>
            <select id="field" value={formData.field} onChange={(e) => setFormData({...formData, field: e.target.value})} className="w-full p-2 border rounded-md">
              <option value="">Select thesis area (optional)</option>
              {thesisAreas.map(area => (
                <option key={area} value={area}>{area.replace(/_/g, ' ')}</option>
              ))}
            </select>
          </div>
        </div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter validation remarks"/></div>
        <Button onClick={() => handleSubmit('validate', formData)} disabled={loading || !formData.thesisId || !formData.mentorId} className="w-full">{loading ? 'Validating...' : 'Validate Thesis'}</Button>
      </>
    );
  };

  // Table columns definitions
  const defendedColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'defenseDate', header: 'Defense Date' },
    { key: 'successful', header: 'Successful', render: (val: boolean) => <Badge variant={val ? "default" : "destructive"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'finalGrade', header: 'Final Grade' },
    { key: 'defenseRemarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Marked At' },
  ];

  const scheduleColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'mentorId', header: 'Mentor ID' },
    { key: 'defenseDate', header: 'Defense Date' },
    { key: 'roomId', header: 'Room ID' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Scheduled At' },
  ];

  const commissionColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'mentorId', header: 'Mentor ID' },
    { key: 'commissionMember1Id', header: 'Member 1 ID' },
    { key: 'commissionMember2Id', header: 'Member 2 ID' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Selected At' },
  ];

  const validationColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'mentorId', header: 'Mentor ID' },
    { key: 'approved', header: 'Approved', render: (val: boolean) => <Badge variant={val ? "default" : "destructive"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'field', header: 'Field' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Validated At' },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-foreground">Mentor Dashboard</h1>
        <p className="text-muted-foreground mt-1">Manage thesis mentoring activities and validations</p>
      </div>

      {success && <Alert><CheckCircle className="h-4 w-4"/><AlertDescription>{success}</AlertDescription></Alert>}
      {error && <Alert variant="destructive"><AlertCircle className="h-4 w-4"/><AlertDescription>{error}</AlertDescription></Alert>}

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ActionCard title="Mark Thesis as Defended" description="Mark a thesis as successfully defended" icon={<GraduationCap className="h-5 w-5"/>} action="markDefended"><MarkDefendedForm /></ActionCard>
        <ActionCard title="Schedule Defense" description="Schedule thesis defense session" icon={<Calendar className="h-5 w-5"/>} action="scheduleDefense"><ScheduleDefenseForm /></ActionCard>
        <ActionCard title="Select Commission Members" description="Select commission members for thesis evaluation" icon={<Users className="h-5 w-5"/>} action="selectCommission"><SelectCommissionForm /></ActionCard>
        <ActionCard title="Validate Thesis" description="Validate thesis by mentor" icon={<Shield className="h-5 w-5"/>} action="validate"><ValidateThesisForm /></ActionCard>
      </div>

      {/* Tables Section */}
      <div className="space-y-6">
        <DataTable 
          data={defendedTheses} 
          columns={defendedColumns} 
          title="Defended Theses" 
        />
        
        <DataTable 
          data={scheduledDefenses} 
          columns={scheduleColumns} 
          title="Scheduled Defenses" 
        />
        
        <DataTable 
          data={selectedCommissions} 
          columns={commissionColumns} 
          title="Selected Commission Members" 
        />
        
        <DataTable 
          data={validatedTheses} 
          columns={validationColumns} 
          title="Validated Theses" 
        />
      </div>
    </div>
  );
}
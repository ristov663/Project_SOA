import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { Archive, CheckCircle, FileText, Shield, User, Calendar, AlertCircle, Table } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";

const BACKEND_URL = "http://localhost:9091";

// API service functions
const administrationService = {
  async archiveThesis(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/archive-thesis`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateByAdministration(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/validate-by-administration`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateByCommission(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/validate-by-commission`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateBySecretary(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/validate-by-secretary`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateSecondSecretaryPhase(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/validate-second-secretary-phase`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateThirdSecretaryPhase(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/validate-third-secretary-phase`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async validateFourthSecretaryPhase(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/validate-fourth-secretary-phase`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
  async secondaryValidateByTeachingCommission(data: any) {
    const response = await fetch(`${BACKEND_URL}/administration/master-thesis/secondary-validate-by-teaching-commission`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
  },
};

export default function ThesisAdministration() {
  const [activeAction, setActiveAction] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  // Tables data state
  const [archivedTheses, setArchivedTheses] = useState<any[]>([]);
  const [adminValidations, setAdminValidations] = useState<any[]>([]);
  const [commissionValidations, setCommissionValidations] = useState<any[]>([]);
  const [secretaryValidations, setSecretaryValidations] = useState<any[]>([]);
  const [secondSecretaryPhases, setSecondSecretaryPhases] = useState<any[]>([]);
  const [thirdSecretaryPhases, setThirdSecretaryPhases] = useState<any[]>([]);
  const [fourthSecretaryPhases, setFourthSecretaryPhases] = useState<any[]>([]);
  const [secondaryCommissionValidations, setSecondaryCommissionValidations] = useState<any[]>([]);

  const clearMessages = () => {
    setSuccess(null);
    setError(null);
  };

  // Mapping functions for backend payloads
  const mapAdminValidationPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    administratorId: { value: formData.administratorId },
    approved: formData.approved,
    remarks: formData.remarks?.trim() || null,
    documentsVerified: formData.documentsVerified,
    studentEligibilityConfirmed: formData.studentEligibilityConfirmed,
  });

  const mapArchivePayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    administratorId: { value: formData.administratorId },
    processValidated: formData.processValidated,
    remarks: formData.remarks?.trim() || null,
  });

  const mapCommissionPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    commissionCoordinatorId: { value: formData.commissionCoordinatorId },
    approved: formData.approved,
    remarks: formData.remarks?.trim() || null,
  });

  const mapSecretaryPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    secretaryId: { value: formData.secretaryId },
    archiveNumber: { value: formData.archiveNumber },
    remarks: formData.remarks?.trim() || null,
  });

  const mapSecondSecretaryPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    secretaryId: { value: formData.secretaryId },
    commissionArchiveNumber: { value: formData.commissionArchiveNumber },
    remarks: formData.remarks?.trim() || null,
  });

  const mapThirdSecretaryPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    secretaryId: { value: formData.secretaryId },
    archiveNumber: { value: formData.archiveNumber },
    remarks: formData.remarks?.trim() || null,
  });

  const mapFourthSecretaryPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    secretaryId: { value: formData.secretaryId },
    archiveNumber: { value: formData.archiveNumber },
    remarks: formData.remarks?.trim() || null,
  });

  const mapSecondaryCommissionPayload = (formData: any) => ({
    thesisId: { value: formData.thesisId },
    commissionCoordinatorId: { value: formData.commissionCoordinatorId },
    approved: formData.approved,
    remarks: formData.remarks?.trim() || null,
  });

  const handleSubmit = async (action: string, formData: any) => {
    setLoading(true);
    clearMessages();

    try {
      let payload;
      let tableData;
      
      switch (action) {
        case 'archive':
          payload = mapArchivePayload(formData);
          await administrationService.archiveThesis(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setArchivedTheses(prev => [...prev, tableData]);
          setSuccess('Thesis archived successfully');
          break;
        case 'validateAdmin':
          payload = mapAdminValidationPayload(formData);
          await administrationService.validateByAdministration(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setAdminValidations(prev => [...prev, tableData]);
          setSuccess('Thesis validated by administration');
          break;
        case 'validateCommission':
          payload = mapCommissionPayload(formData);
          await administrationService.validateByCommission(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setCommissionValidations(prev => [...prev, tableData]);
          setSuccess('Thesis validated by commission');
          break;
        case 'validateSecretary':
          payload = mapSecretaryPayload(formData);
          await administrationService.validateBySecretary(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setSecretaryValidations(prev => [...prev, tableData]);
          setSuccess('Thesis validated by secretary');
          break;
        case 'validateSecond':
          payload = mapSecondSecretaryPayload(formData);
          await administrationService.validateSecondSecretaryPhase(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setSecondSecretaryPhases(prev => [...prev, tableData]);
          setSuccess('Second phase validation completed');
          break;
        case 'validateThird':
          payload = mapThirdSecretaryPayload(formData);
          await administrationService.validateThirdSecretaryPhase(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setThirdSecretaryPhases(prev => [...prev, tableData]);
          setSuccess('Third phase validation completed');
          break;
        case 'validateFourth':
          payload = mapFourthSecretaryPayload(formData);
          await administrationService.validateFourthSecretaryPhase(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setFourthSecretaryPhases(prev => [...prev, tableData]);
          setSuccess('Fourth phase validation completed');
          break;
        case 'secondaryCommission':
          payload = mapSecondaryCommissionPayload(formData);
          await administrationService.secondaryValidateByTeachingCommission(payload);
          tableData = { ...formData, timestamp: new Date().toLocaleString() };
          setSecondaryCommissionValidations(prev => [...prev, tableData]);
          setSuccess('Secondary teaching commission validation completed');
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
  const ArchiveForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', administratorId: '', processValidated: false, remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="administratorId">Administrator ID</Label><Input id="administratorId" value={formData.administratorId} onChange={(e) => setFormData({...formData, administratorId: e.target.value})} placeholder="Enter administrator ID"/></div>
        </div>
        <div className="flex items-center space-x-2">
          <Checkbox id="processValidated" checked={formData.processValidated} onCheckedChange={(checked) => setFormData({...formData, processValidated: !!checked})}/>
          <Label htmlFor="processValidated">Process Validated</Label>
        </div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('archive', formData)} disabled={loading || !formData.thesisId || !formData.administratorId} className="w-full">{loading ? 'Archiving...' : 'Archive Thesis'}</Button>
      </>
    );
  };

  const AdminValidationForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', administratorId: '', approved: false, remarks: '', documentsVerified: false, studentEligibilityConfirmed: false });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="administratorId">Administrator ID</Label><Input id="administratorId" value={formData.administratorId} onChange={(e) => setFormData({...formData, administratorId: e.target.value})} placeholder="Enter administrator ID"/></div>
        </div>
        <div className="space-y-2">
          <div className="flex items-center space-x-2"><Checkbox id="approved" checked={formData.approved} onCheckedChange={(checked) => setFormData({...formData, approved: !!checked})}/><Label htmlFor="approved">Approved</Label></div>
          <div className="flex items-center space-x-2"><Checkbox id="documentsVerified" checked={formData.documentsVerified} onCheckedChange={(checked) => setFormData({...formData, documentsVerified: !!checked})}/><Label htmlFor="documentsVerified">Documents Verified</Label></div>
          <div className="flex items-center space-x-2"><Checkbox id="studentEligibilityConfirmed" checked={formData.studentEligibilityConfirmed} onCheckedChange={(checked) => setFormData({...formData, studentEligibilityConfirmed: !!checked})}/><Label htmlFor="studentEligibilityConfirmed">Student Eligibility Confirmed</Label></div>
        </div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('validateAdmin', formData)} disabled={loading || !formData.thesisId || !formData.administratorId} className="w-full">{loading ? 'Validating...' : 'Validate by Administration'}</Button>
      </>
    );
  };

  const CommissionValidationForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', commissionCoordinatorId: '', approved: false, remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="commissionCoordinatorId">Coordinator ID</Label><Input id="commissionCoordinatorId" value={formData.commissionCoordinatorId} onChange={(e) => setFormData({...formData, commissionCoordinatorId: e.target.value})} placeholder="Enter coordinator ID"/></div>
        </div>
        <div className="flex items-center space-x-2"><Checkbox id="approved" checked={formData.approved} onCheckedChange={(checked) => setFormData({...formData, approved: !!checked})}/><Label htmlFor="approved">Approved</Label></div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('validateCommission', formData)} disabled={loading || !formData.thesisId || !formData.commissionCoordinatorId} className="w-full">{loading ? 'Validating...' : 'Validate by Commission'}</Button>
      </>
    );
  };

  const SecretaryValidationForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', secretaryId: '', archiveNumber: '', remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="secretaryId">Secretary ID</Label><Input id="secretaryId" value={formData.secretaryId} onChange={(e) => setFormData({...formData, secretaryId: e.target.value})} placeholder="Enter secretary ID"/></div>
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="archiveNumber">Archive Number</Label><Input id="archiveNumber" value={formData.archiveNumber} onChange={(e) => setFormData({...formData, archiveNumber: e.target.value})} placeholder="Enter archive number"/></div>
        </div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('validateSecretary', formData)} disabled={loading || !formData.thesisId || !formData.secretaryId} className="w-full">{loading ? 'Validating...' : 'Validate by Secretary'}</Button>
      </>
    );
  };

  const SecondSecretaryPhaseForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', secretaryId: '', commissionArchiveNumber: '', remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="secretaryId">Secretary ID</Label><Input id="secretaryId" value={formData.secretaryId} onChange={(e) => setFormData({...formData, secretaryId: e.target.value})} placeholder="Enter secretary ID"/></div>
        </div>
        <div><Label htmlFor="commissionArchiveNumber">Commission Archive Number</Label><Input id="commissionArchiveNumber" value={formData.commissionArchiveNumber} onChange={(e) => setFormData({...formData, commissionArchiveNumber: e.target.value})} placeholder="Enter commission archive number"/></div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('validateSecond', formData)} disabled={loading || !formData.thesisId || !formData.secretaryId} className="w-full">{loading ? 'Validating...' : 'Validate Second Phase'}</Button>
      </>
    );
  };

  const ThirdSecretaryPhaseForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', secretaryId: '', archiveNumber: '', remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="secretaryId">Secretary ID</Label><Input id="secretaryId" value={formData.secretaryId} onChange={(e) => setFormData({...formData, secretaryId: e.target.value})} placeholder="Enter secretary ID"/></div>
        </div>
        <div><Label htmlFor="archiveNumber">Archive Number</Label><Input id="archiveNumber" value={formData.archiveNumber} onChange={(e) => setFormData({...formData, archiveNumber: e.target.value})} placeholder="Enter archive number"/></div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('validateThird', formData)} disabled={loading || !formData.thesisId || !formData.secretaryId} className="w-full">{loading ? 'Validating...' : 'Validate Third Phase'}</Button>
      </>
    );
  };

  const FourthSecretaryPhaseForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', secretaryId: '', archiveNumber: '', remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="secretaryId">Secretary ID</Label><Input id="secretaryId" value={formData.secretaryId} onChange={(e) => setFormData({...formData, secretaryId: e.target.value})} placeholder="Enter secretary ID"/></div>
        </div>
        <div><Label htmlFor="archiveNumber">Archive Number</Label><Input id="archiveNumber" value={formData.archiveNumber} onChange={(e) => setFormData({...formData, archiveNumber: e.target.value})} placeholder="Enter archive number"/></div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('validateFourth', formData)} disabled={loading || !formData.thesisId || !formData.secretaryId} className="w-full">{loading ? 'Validating...' : 'Validate Fourth Phase'}</Button>
      </>
    );
  };

  const SecondaryCommissionForm = () => {
    const [formData, setFormData] = useState({ thesisId: '', commissionCoordinatorId: '', approved: false, remarks: '' });
    return (
      <>
        <div className="grid grid-cols-2 gap-4">
          <div><Label htmlFor="thesisId">Thesis ID</Label><Input id="thesisId" value={formData.thesisId} onChange={(e) => setFormData({...formData, thesisId: e.target.value})} placeholder="Enter thesis ID"/></div>
          <div><Label htmlFor="commissionCoordinatorId">Coordinator ID</Label><Input id="commissionCoordinatorId" value={formData.commissionCoordinatorId} onChange={(e) => setFormData({...formData, commissionCoordinatorId: e.target.value})} placeholder="Enter coordinator ID"/></div>
        </div>
        <div className="flex items-center space-x-2"><Checkbox id="approved" checked={formData.approved} onCheckedChange={(checked) => setFormData({...formData, approved: !!checked})}/><Label htmlFor="approved">Approved</Label></div>
        <div><Label htmlFor="remarks">Remarks</Label><Textarea id="remarks" value={formData.remarks} onChange={(e) => setFormData({...formData, remarks: e.target.value})} placeholder="Enter remarks"/></div>
        <Button onClick={() => handleSubmit('secondaryCommission', formData)} disabled={loading || !formData.thesisId || !formData.commissionCoordinatorId} className="w-full">{loading ? 'Validating...' : 'Secondary Commission Validation'}</Button>
      </>
    );
  };

  // Table columns definitions
  const archiveColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'administratorId', header: 'Administrator ID' },
    { key: 'processValidated', header: 'Process Validated', render: (val: boolean) => <Badge variant={val ? "default" : "secondary"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  const adminValidationColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'administratorId', header: 'Administrator ID' },
    { key: 'approved', header: 'Approved', render: (val: boolean) => <Badge variant={val ? "default" : "destructive"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'documentsVerified', header: 'Documents Verified', render: (val: boolean) => <Badge variant={val ? "default" : "secondary"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'studentEligibilityConfirmed', header: 'Eligibility Confirmed', render: (val: boolean) => <Badge variant={val ? "default" : "secondary"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  const commissionColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'commissionCoordinatorId', header: 'Coordinator ID' },
    { key: 'approved', header: 'Approved', render: (val: boolean) => <Badge variant={val ? "default" : "destructive"}>{val ? 'Yes' : 'No'}</Badge> },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  const secretaryColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'secretaryId', header: 'Secretary ID' },
    { key: 'archiveNumber', header: 'Archive Number' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  const secondSecretaryColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'secretaryId', header: 'Secretary ID' },
    { key: 'commissionArchiveNumber', header: 'Commission Archive Number' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  const thirdSecretaryColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'secretaryId', header: 'Secretary ID' },
    { key: 'archiveNumber', header: 'Archive Number' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  const fourthSecretaryColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'secretaryId', header: 'Secretary ID' },
    { key: 'archiveNumber', header: 'Archive Number' },
    { key: 'remarks', header: 'Remarks' },
    { key: 'timestamp', header: 'Timestamp' },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-foreground">Thesis Administration</h1>
        <p className="text-muted-foreground mt-1">Master thesis administration and validation actions</p>
      </div>

      {success && <Alert><CheckCircle className="h-4 w-4"/><AlertDescription>{success}</AlertDescription></Alert>}
      {error && <Alert variant="destructive"><AlertCircle className="h-4 w-4"/><AlertDescription>{error}</AlertDescription></Alert>}

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ActionCard title="Archive Thesis" description="Archive a completed thesis" icon={<Archive className="h-5 w-5"/>} action="archive"><ArchiveForm /></ActionCard>
        <ActionCard title="Administrative Validation" description="Validate thesis by administration" icon={<Shield className="h-5 w-5"/>} action="validateAdmin"><AdminValidationForm /></ActionCard>
        <ActionCard title="Commission Validation" description="Validate thesis by commission" icon={<CheckCircle className="h-5 w-5"/>} action="validateCommission"><CommissionValidationForm /></ActionCard>
        <ActionCard title="Secretary Validation" description="First phase secretary validation" icon={<User className="h-5 w-5"/>} action="validateSecretary"><SecretaryValidationForm /></ActionCard>
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2"><FileText className="h-5 w-5"/>Secretary Phase Validations</CardTitle>
          <CardDescription>Multi-phase secretary validation process</CardDescription>
        </CardHeader>
        <CardContent className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {activeAction === 'validateSecond' ? (
            <div className="md:col-span-3">
              <SecondSecretaryPhaseForm />
              <Button 
                variant="outline" 
                onClick={() => setActiveAction(null)} 
                className="mt-2"
                disabled={loading}
              >
                Cancel
              </Button>
            </div>
          ) : (
            <Button variant="outline" onClick={() => {setActiveAction('validateSecond'); clearMessages();}} disabled={loading}>Second Phase</Button>
          )}
          
          {activeAction === 'validateThird' ? (
            <div className="md:col-span-3">
              <ThirdSecretaryPhaseForm />
              <Button 
                variant="outline" 
                onClick={() => setActiveAction(null)} 
                className="mt-2"
                disabled={loading}
              >
                Cancel
              </Button>
            </div>
          ) : activeAction !== 'validateSecond' && (
            <Button variant="outline" onClick={() => {setActiveAction('validateThird'); clearMessages();}} disabled={loading}>Third Phase</Button>
          )}
          
          {activeAction === 'validateFourth' ? (
            <div className="md:col-span-3">
              <FourthSecretaryPhaseForm />
              <Button 
                variant="outline" 
                onClick={() => setActiveAction(null)} 
                className="mt-2"
                disabled={loading}
              >
                Cancel
              </Button>
            </div>
          ) : activeAction !== 'validateSecond' && activeAction !== 'validateThird' && (
            <Button variant="outline" onClick={() => {setActiveAction('validateFourth'); clearMessages();}} disabled={loading}>Fourth Phase</Button>
          )}
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2"><Calendar className="h-5 w-5"/>Additional Validations</CardTitle>
          <CardDescription>Secondary validation processes</CardDescription>
        </CardHeader>
        <CardContent>
          {activeAction === 'secondaryCommission' ? (
            <div>
              <SecondaryCommissionForm />
              <Button 
                variant="outline" 
                onClick={() => setActiveAction(null)} 
                className="mt-2"
                disabled={loading}
              >
                Cancel
              </Button>
            </div>
          ) : (
            <Button variant="outline" onClick={() => {setActiveAction('secondaryCommission'); clearMessages();}} disabled={loading}>Secondary Teaching Commission Validation</Button>
          )}
        </CardContent>
      </Card>

      {/* Tables Section */}
      <div className="space-y-6">
        <DataTable 
          data={archivedTheses} 
          columns={archiveColumns} 
          title="Archived Theses" 
        />
        
        <DataTable 
          data={adminValidations} 
          columns={adminValidationColumns} 
          title="Administrative Validations" 
        />
        
        <DataTable 
          data={commissionValidations} 
          columns={commissionColumns} 
          title="Commission Validations" 
        />
        
        <DataTable 
          data={secretaryValidations} 
          columns={secretaryColumns} 
          title="Secretary Validations (First Phase)" 
        />
        
        <DataTable 
          data={secondSecretaryPhases} 
          columns={secondSecretaryColumns} 
          title="Secretary Validations (Second Phase)" 
        />
        
        <DataTable 
          data={thirdSecretaryPhases} 
          columns={thirdSecretaryColumns} 
          title="Secretary Validations (Third Phase)" 
        />
        
        <DataTable 
          data={fourthSecretaryPhases} 
          columns={fourthSecretaryColumns} 
          title="Secretary Validations (Fourth Phase)" 
        />
        
        <DataTable 
          data={secondaryCommissionValidations} 
          columns={commissionColumns} 
          title="Secondary Teaching Commission Validations" 
        />
      </div>
    </div>
  );
}
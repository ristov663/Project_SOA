import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { FileText, AlertCircle, CheckCircle, Table, Plus, X } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";

const BACKEND_URL = "http://localhost:9091";

interface DocumentInfo {
  documentId: string;
  fileName: string;
  fileSize: number;
  mimeType: string;
  uploadDate: string;
  uploadedBy: { value: string };
  documentType: string;
}

interface SubmitThesisRegistrationDto {
  thesisId: { value: string };
  studentId: { value: string };
  mentorId: { value: string };
  title: { value: string };
  shortDescription: { value: string };
  requiredDocuments: DocumentInfo[];
  submissionDate: string; // This should be ISO string for Instant parsing
}

// API service
const studentThesisService = {
  async submitThesisRegistration(data: SubmitThesisRegistrationDto): Promise<any> {
    const response = await fetch(`${BACKEND_URL}/student/master-thesis/submit-thesis`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
    }

    return await response.json();
  },
};

export default function StudentThesisSubmission() {
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  // Table data for submitted theses
  const [submittedTheses, setSubmittedTheses] = useState<any[]>([]);

  // Form data
  const [formData, setFormData] = useState({
    thesisId: '',
    studentId: '',
    mentorId: '',
    title: '',
    shortDescription: '',
    requiredDocuments: [
      {
        documentId: crypto.randomUUID(),
        fileName: 'thesis-justification.pdf',
        fileSize: 1024000,
        mimeType: 'application/pdf',
        uploadDate: new Date().toISOString(),
        uploadedBy: '',
        documentType: 'THESIS_JUSTIFICATION'
      }
    ] as DocumentInfo[],
    submissionDate: new Date().toISOString(),
  });

  const clearMessages = () => {
    setSuccess(null);
    setError(null);
  };

  const mapSubmissionPayload = (formData: any): SubmitThesisRegistrationDto => ({
    thesisId: { value: formData.thesisId },
    studentId: { value: formData.studentId },
    mentorId: { value: formData.mentorId },
    title: { value: formData.title },
    shortDescription: { value: formData.shortDescription },
    requiredDocuments: formData.requiredDocuments.map((doc: any) => ({
      documentId: doc.documentId,
      fileName: doc.fileName,
      fileSize: doc.fileSize,
      mimeType: doc.mimeType,
      uploadDate: doc.uploadDate,
      uploadedBy: { value: formData.studentId },
      documentType: doc.documentType
    })),
    // Ensure submissionDate is in proper ISO format for Instant parsing
    submissionDate: formData.submissionDate.endsWith('Z') 
      ? formData.submissionDate 
      : new Date(formData.submissionDate).toISOString(),
  });

  const handleSubmit = async () => {
    setLoading(true);
    clearMessages();

    try {
      const payload = mapSubmissionPayload(formData);
      console.log('Sending payload:', JSON.stringify(payload, null, 2)); // Debug log
      
      const result = await studentThesisService.submitThesisRegistration(payload);
      
      // Add to local submitted theses table
      const tableData = {
        ...formData,
        timestamp: new Date().toLocaleString(),
        status: 'Submitted'
      };
      setSubmittedTheses(prev => [...prev, tableData]);
      
      // Add to global thesis store (this will update the main thesis page)
      if (window.globalThesisStore) {
        window.globalThesisStore.addStudentSubmission({
          thesisId: formData.thesisId,
          studentId: formData.studentId,
          mentorId: formData.mentorId,
          title: formData.title,
          shortDescription: formData.shortDescription,
          status: 'Submitted',
          submissionDate: formData.submissionDate,
          requiredDocuments: formData.requiredDocuments
        });
      }
      
      setSuccess(result.message || 'Thesis registration submitted successfully');
      
      // Reset form
      setFormData({
        thesisId: '',
        studentId: '',
        mentorId: '',
        title: '',
        shortDescription: '',
        requiredDocuments: [
          {
            documentId: crypto.randomUUID(),
            fileName: 'thesis-justification.pdf',
            fileSize: 1024000,
            mimeType: 'application/pdf',
            uploadDate: new Date().toISOString(),
            uploadedBy: '',
            documentType: 'THESIS_JUSTIFICATION'
          }
        ],
        submissionDate: new Date().toISOString(),
      });
    } catch (err) {
      console.error('Submission error:', err);
      setError(err instanceof Error ? err.message : 'Failed to submit thesis registration');
    } finally {
      setLoading(false);
    }
  };

  const addDocument = () => {
    setFormData(prev => ({
      ...prev,
      requiredDocuments: [
        ...prev.requiredDocuments,
        {
          documentId: crypto.randomUUID(),
          fileName: '',
          fileSize: 0,
          mimeType: 'application/pdf',
          uploadDate: new Date().toISOString(),
          uploadedBy: prev.studentId,
          documentType: 'THESIS_JUSTIFICATION'
        }
      ]
    }));
  };

  const removeDocument = (index: number) => {
    setFormData(prev => ({
      ...prev,
      requiredDocuments: prev.requiredDocuments.filter((_, i) => i !== index)
    }));
  };

  const updateDocument = (index: number, field: keyof DocumentInfo, value: any) => {
    setFormData(prev => ({
      ...prev,
      requiredDocuments: prev.requiredDocuments.map((doc, i) => 
        i === index ? { ...doc, [field]: value } : doc
      )
    }));
  };

  // Document type options based on your enum
  const documentTypes = [
    'THESIS_JUSTIFICATION',
    'RESEARCH_PROPOSAL',
    'LITERATURE_REVIEW',
    'METHODOLOGY',
    'RESULTS',
    'CONCLUSION',
    'APPENDIX',
    'BIBLIOGRAPHY'
  ];

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
          <p className="text-muted-foreground text-center py-4">No submissions yet</p>
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

  // Table columns for submitted theses
  const submissionColumns = [
    { key: 'thesisId', header: 'Thesis ID' },
    { key: 'studentId', header: 'Student ID' },
    { key: 'mentorId', header: 'Mentor ID' },
    { key: 'title', header: 'Title' },
    { 
      key: 'shortDescription', 
      header: 'Description',
      render: (desc: string) => (
        <div className="max-w-xs truncate" title={desc}>
          {desc}
        </div>
      )
    },
    { 
      key: 'requiredDocuments', 
      header: 'Documents',
      render: (docs: DocumentInfo[]) => (
        <div className="flex flex-wrap gap-1">
          {docs.map((doc, idx) => (
            <Badge key={idx} variant="secondary" className="text-xs">
              {doc.documentType.replace(/_/g, ' ')}
            </Badge>
          ))}
        </div>
      )
    },
    { 
      key: 'status', 
      header: 'Status',
      render: (status: string) => (
        <Badge variant="default">{status}</Badge>
      )
    },
    { key: 'timestamp', header: 'Submitted At' },
  ];

  const isFormValid = formData.thesisId && 
    formData.studentId && 
    formData.mentorId && 
    formData.title && 
    formData.shortDescription && 
    formData.shortDescription.length >= 10 && 
    formData.shortDescription.length <= 1000;

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-foreground">Submit Thesis Registration</h1>
        <p className="text-muted-foreground mt-1">Register your master's thesis for review and approval</p>
      </div>

      {success && (
        <Alert>
          <CheckCircle className="h-4 w-4" />
          <AlertDescription>{success}</AlertDescription>
        </Alert>
      )}

      {error && (
        <Alert variant="destructive">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {/* Thesis Registration Form */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <FileText className="h-5 w-5" />
            Thesis Registration Form
          </CardTitle>
          <CardDescription>
            Fill out all required fields to submit your thesis registration
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-6">
          {/* Basic Information */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <Label htmlFor="thesisId">Thesis ID *</Label>
              <Input
                id="thesisId"
                value={formData.thesisId}
                onChange={(e) => setFormData({...formData, thesisId: e.target.value})}
                placeholder="e.g., TH-2024-001"
              />
            </div>
            <div>
              <Label htmlFor="studentId">Student ID *</Label>
              <Input
                id="studentId"
                value={formData.studentId}
                onChange={(e) => setFormData({...formData, studentId: e.target.value})}
                placeholder="e.g., S-123456"
              />
            </div>
            <div>
              <Label htmlFor="mentorId">Mentor ID *</Label>
              <Input
                id="mentorId"
                value={formData.mentorId}
                onChange={(e) => setFormData({...formData, mentorId: e.target.value})}
                placeholder="e.g., P-789012"
              />
            </div>
          </div>

          {/* Thesis Details */}
          <div>
            <Label htmlFor="title">Thesis Title *</Label>
            <Input
              id="title"
              value={formData.title}
              onChange={(e) => setFormData({...formData, title: e.target.value})}
              placeholder="Enter your thesis title"
            />
          </div>

          <div>
            <Label htmlFor="shortDescription">Short Description *</Label>
            <Textarea
              id="shortDescription"
              value={formData.shortDescription}
              onChange={(e) => setFormData({...formData, shortDescription: e.target.value})}
              placeholder="Provide a brief description of your thesis research (minimum 10 characters, maximum 1000)..."
              rows={4}
              className={
                formData.shortDescription.length > 0 && 
                (formData.shortDescription.length < 10 || formData.shortDescription.length > 1000)
                  ? 'border-red-500' 
                  : ''
              }
            />
            <div className="flex justify-between text-sm mt-1">
              <span className={
                formData.shortDescription.length < 10 
                  ? 'text-red-500' 
                  : formData.shortDescription.length > 1000 
                    ? 'text-red-500' 
                    : 'text-muted-foreground'
              }>
                {formData.shortDescription.length < 10 
                  ? `Need ${10 - formData.shortDescription.length} more characters (minimum 10)`
                  : formData.shortDescription.length > 1000
                    ? `${formData.shortDescription.length - 1000} characters over limit`
                    : 'Valid length'
                }
              </span>
              <span className={
                formData.shortDescription.length > 1000 ? 'text-red-500' : 'text-muted-foreground'
              }>
                {formData.shortDescription.length}/1000
              </span>
            </div>
          </div>

          {/* Required Documents */}
          <div>
            <div className="flex items-center justify-between mb-4">
              <Label>Required Documents</Label>
              <Button
                type="button"
                variant="outline"
                size="sm"
                onClick={addDocument}
              >
                <Plus className="h-4 w-4 mr-2" />
                Add Document
              </Button>
            </div>
            
            <div className="space-y-3">
              {formData.requiredDocuments.map((doc, index) => (
                <div key={index} className="p-4 border rounded-md space-y-3">
                  <div className="grid grid-cols-2 gap-3">
                    <div>
                      <Label>File Name</Label>
                      <Input
                        value={doc.fileName}
                        onChange={(e) => updateDocument(index, 'fileName', e.target.value)}
                        placeholder="e.g., thesis-justification.pdf"
                      />
                    </div>
                    <div>
                      <Label>File Size (bytes)</Label>
                      <Input
                        type="number"
                        value={doc.fileSize}
                        onChange={(e) => updateDocument(index, 'fileSize', parseInt(e.target.value) || 0)}
                        placeholder="File size in bytes"
                      />
                    </div>
                  </div>
                  
                  <div className="grid grid-cols-2 gap-3">
                    <div>
                      <Label>MIME Type</Label>
                      <select
                        value={doc.mimeType}
                        onChange={(e) => updateDocument(index, 'mimeType', e.target.value)}
                        className="w-full p-2 border rounded-md"
                      >
                        <option value="application/pdf">application/pdf</option>
                        <option value="application/msword">application/msword</option>
                        <option value="application/vnd.openxmlformats-officedocument.wordprocessingml.document">application/vnd.openxmlformats-officedocument.wordprocessingml.document</option>
                        <option value="image/jpeg">image/jpeg</option>
                        <option value="image/png">image/png</option>
                      </select>
                    </div>
                    <div>
                      <Label>Document Type</Label>
                      <select
                        value={doc.documentType}
                        onChange={(e) => updateDocument(index, 'documentType', e.target.value)}
                        className="w-full p-2 border rounded-md"
                      >
                        {documentTypes.map(type => (
                          <option key={type} value={type}>
                            {type.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase())}
                          </option>
                        ))}
                      </select>
                    </div>
                  </div>

                  <div className="flex items-center justify-between">
                    <div>
                      <Label className="text-sm text-muted-foreground">Document ID: {doc.documentId}</Label>
                    </div>
                    <Button
                      type="button"
                      variant="ghost"
                      size="sm"
                      onClick={() => removeDocument(index)}
                      disabled={formData.requiredDocuments.length <= 1}
                    >
                      <X className="h-4 w-4" />
                      Remove
                    </Button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Submission Date */}
          <div>
            <Label htmlFor="submissionDate">Submission Date</Label>
            <Input
              id="submissionDate"
              type="datetime-local"
              value={formData.submissionDate.slice(0, 16)}
              onChange={(e) => setFormData({...formData, submissionDate: new Date(e.target.value).toISOString()})}
            />
          </div>

          {/* Submit Button */}
          <Button
            onClick={handleSubmit}
            disabled={loading || !isFormValid}
            className="w-full"
            size="lg"
          >
            {loading ? 'Submitting...' : 'Submit Thesis Registration'}
          </Button>
        </CardContent>
      </Card>

      {/* Submitted Theses Table */}
      <DataTable 
        data={submittedTheses} 
        columns={submissionColumns} 
        title="Your Thesis Submissions" 
      />
    </div>
  );
}
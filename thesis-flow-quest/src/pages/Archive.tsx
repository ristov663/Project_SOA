// import { useState, useEffect } from "react";
// import { Button } from "@/components/ui/button";
// import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
// import { Badge } from "@/components/ui/badge";
// import { Input } from "@/components/ui/input";
// import { Textarea } from "@/components/ui/textarea";
// import { Label } from "@/components/ui/label";
// import { 
//   Archive, 
//   Search, 
//   Calendar, 
//   FileText, 
//   User, 
//   Shield, 
//   AlertCircle, 
//   CheckCircle, 
//   MoreHorizontal, 
//   Eye, 
//   Download, 
//   Trash2,
//   Filter,
//   RefreshCw,
//   Database
// } from "lucide-react";
// import { Alert, AlertDescription } from "@/components/ui/alert";
// import {
//   DropdownMenu,
//   DropdownMenuContent,
//   DropdownMenuItem,
//   DropdownMenuLabel,
//   DropdownMenuSeparator,
//   DropdownMenuTrigger,
// } from "@/components/ui/dropdown-menu";

// // Global archive store - similar pattern to your thesis store
// if (!window.globalArchiveStore) {
//   window.globalArchiveStore = {
//     data: {
//       archivedTheses: [],
//       adminValidations: [],
//       commissionValidations: [],
//       secretaryValidations: [],
//       secondSecretaryPhases: [],
//       thirdSecretaryPhases: [],
//       fourthSecretaryPhases: [],
//       secondaryCommissionValidations: []
//     },
//     listeners: new Set(),

//     // Subscribe to changes
//     subscribe(callback) {
//       this.listeners.add(callback);
//       return () => this.listeners.delete(callback);
//     },

//     // Notify all listeners
//     notify() {
//       this.listeners.forEach(callback => callback(this.data));
//     },

//     // Add archived thesis
//     addArchivedThesis(archive) {
//       this.data.archivedTheses.push({
//         ...archive,
//         archiveDate: new Date().toLocaleString(),
//         archiveId: `ARCH-${Date.now()}`,
//         status: 'Archived'
//       });
//       this.notify();
//     },

//     // Add validation records
//     addAdminValidation(validation) {
//       this.data.adminValidations.push({
//         ...validation,
//         validationDate: new Date().toLocaleString(),
//         validationId: `ADM-${Date.now()}`
//       });
//       this.notify();
//     },

//     addCommissionValidation(validation) {
//       this.data.commissionValidations.push({
//         ...validation,
//         validationDate: new Date().toLocaleString(),
//         validationId: `COMM-${Date.now()}`
//       });
//       this.notify();
//     },

//     addSecretaryValidation(validation) {
//       this.data.secretaryValidations.push({
//         ...validation,
//         validationDate: new Date().toLocaleString(),
//         validationId: `SEC-${Date.now()}`
//       });
//       this.notify();
//     },

//     addSecondSecretaryPhase(phase) {
//       this.data.secondSecretaryPhases.push({
//         ...phase,
//         validationDate: new Date().toLocaleString(),
//         validationId: `SEC2-${Date.now()}`
//       });
//       this.notify();
//     },

//     addThirdSecretaryPhase(phase) {
//       this.data.thirdSecretaryPhases.push({
//         ...phase,
//         validationDate: new Date().toLocaleString(),
//         validationId: `SEC3-${Date.now()}`
//       });
//       this.notify();
//     },

//     addFourthSecretaryPhase(phase) {
//       this.data.fourthSecretaryPhases.push({
//         ...phase,
//         validationDate: new Date().toLocaleString(),
//         validationId: `SEC4-${Date.now()}`
//       });
//       this.notify();
//     },

//     addSecondaryCommissionValidation(validation) {
//       this.data.secondaryCommissionValidations.push({
//         ...validation,
//         validationDate: new Date().toLocaleString(),
//         validationId: `SCOMM-${Date.now()}`
//       });
//       this.notify();
//     },

//     // Get all data
//     getAllData() {
//       return this.data;
//     }
//   };
// }

// // Custom hook to use the global archive store
// const useGlobalArchiveStore = () => {
//   const [data, setData] = useState(window.globalArchiveStore.getAllData());

//   useEffect(() => {
//     const unsubscribe = window.globalArchiveStore.subscribe((newData) => {
//       setData({ ...newData });
//     });

//     return unsubscribe;
//   }, []);

//   return {
//     data,
//     addArchivedThesis: (archive) => window.globalArchiveStore.addArchivedThesis(archive),
//     addAdminValidation: (validation) => window.globalArchiveStore.addAdminValidation(validation),
//     addCommissionValidation: (validation) => window.globalArchiveStore.addCommissionValidation(validation),
//     addSecretaryValidation: (validation) => window.globalArchiveStore.addSecretaryValidation(validation),
//     addSecondSecretaryPhase: (phase) => window.globalArchiveStore.addSecondSecretaryPhase(phase),
//     addThirdSecretaryPhase: (phase) => window.globalArchiveStore.addThirdSecretaryPhase(phase),
//     addFourthSecretaryPhase: (phase) => window.globalArchiveStore.addFourthSecretaryPhase(phase),
//     addSecondaryCommissionValidation: (validation) => window.globalArchiveStore.addSecondaryCommissionValidation(validation)
//   };
// };

// // Integration function to be called from Administration page
// window.addToArchive = {
//   archiveThesis: (data) => window.globalArchiveStore.addArchivedThesis(data),
//   adminValidation: (data) => window.globalArchiveStore.addAdminValidation(data),
//   commissionValidation: (data) => window.globalArchiveStore.addCommissionValidation(data),
//   secretaryValidation: (data) => window.globalArchiveStore.addSecretaryValidation(data),
//   secondSecretaryPhase: (data) => window.globalArchiveStore.addSecondSecretaryPhase(data),
//   thirdSecretaryPhase: (data) => window.globalArchiveStore.addThirdSecretaryPhase(data),
//   fourthSecretaryPhase: (data) => window.globalArchiveStore.addFourthSecretaryPhase(data),
//   secondaryCommission: (data) => window.globalArchiveStore.addSecondaryCommissionValidation(data)
// };

// export default function Archive() {
//   const { data } = useGlobalArchiveStore();
  
//   const [searchTerm, setSearchTerm] = useState('');
//   const [selectedCategory, setSelectedCategory] = useState('all');
//   const [filteredData, setFilteredData] = useState(data);
//   const [selectedRecord, setSelectedRecord] = useState(null);
//   const [showDetails, setShowDetails] = useState(false);
//   const [lastUpdated, setLastUpdated] = useState(new Date());

//   // Update filtered data when data or filters change
//   useEffect(() => {
//     let filtered = {};
    
//     Object.keys(data).forEach(key => {
//       filtered[key] = data[key].filter(item => {
//         const matchesSearch = !searchTerm || 
//           Object.values(item).some(value => 
//             String(value).toLowerCase().includes(searchTerm.toLowerCase())
//           );
        
//         const matchesCategory = selectedCategory === 'all' || key === selectedCategory;
        
//         return matchesSearch && matchesCategory;
//       });
//     });
    
//     setFilteredData(filtered);
//     setLastUpdated(new Date());
//   }, [data, searchTerm, selectedCategory]);

//   // Calculate totals
//   const getTotalCount = (category) => {
//     if (category === 'all') {
//       return Object.values(data).reduce((total, items) => total + items.length, 0);
//     }
//     return data[category]?.length || 0;
//   };

//   // Data table component
//   const DataTable = ({ data, columns, title, emptyMessage }) => (
//     <Card className="mt-6">
//       <CardHeader>
//         <CardTitle className="flex items-center gap-2 justify-between">
//           <div className="flex items-center gap-2">
//             <Archive className="h-5 w-5" />
//             {title}
//           </div>
//           <Badge variant="secondary">{data.length} records</Badge>
//         </CardTitle>
//       </CardHeader>
//       <CardContent>
//         {data.length === 0 ? (
//           <div className="text-center py-8 text-muted-foreground">
//             <Archive className="h-12 w-12 mx-auto mb-4 opacity-50" />
//             <p>{emptyMessage}</p>
//           </div>
//         ) : (
//           <div className="overflow-x-auto">
//             <table className="w-full border-collapse border border-gray-300">
//               <thead>
//                 <tr className="bg-gray-50">
//                   {columns.map((col, idx) => (
//                     <th key={idx} className="border border-gray-300 px-4 py-2 text-left font-medium text-sm">
//                       {col.header}
//                     </th>
//                   ))}
//                 </tr>
//               </thead>
//               <tbody>
//                 {data.map((row, idx) => (
//                   <tr key={idx} className={idx % 2 === 0 ? "bg-white" : "bg-gray-50 hover:bg-gray-100"}>
//                     {columns.map((col, colIdx) => (
//                       <td key={colIdx} className="border border-gray-300 px-4 py-2 text-sm">
//                         {col.render ? col.render(row) : row[col.key]}
//                       </td>
//                     ))}
//                   </tr>
//                 ))}
//               </tbody>
//             </table>
//           </div>
//         )}
//       </CardContent>
//     </Card>
//   );

//   // Column definitions
//   const archiveColumns = [
//     { key: 'thesisId', header: 'Thesis ID' },
//     { key: 'administratorId', header: 'Administrator' },
//     { key: 'archiveId', header: 'Archive ID' },
//     { 
//       key: 'processValidated', 
//       header: 'Validated', 
//       render: (item) => (
//         <Badge variant={item.processValidated ? "default" : "secondary"}>
//           {item.processValidated ? 'Yes' : 'No'}
//         </Badge>
//       )
//     },
//     { key: 'archiveDate', header: 'Archive Date' },
//     { key: 'remarks', header: 'Remarks' },
//     {
//       key: 'actions',
//       header: 'Actions',
//       render: (item) => (
//         <DropdownMenu>
//           <DropdownMenuTrigger asChild>
//             <Button variant="ghost" className="h-8 w-8 p-0">
//               <MoreHorizontal className="h-4 w-4" />
//             </Button>
//           </DropdownMenuTrigger>
//           <DropdownMenuContent align="end">
//             <DropdownMenuItem onClick={() => {setSelectedRecord(item); setShowDetails(true);}}>
//               <Eye className="mr-2 h-4 w-4" />
//               View Details
//             </DropdownMenuItem>
//             <DropdownMenuItem>
//               <Download className="mr-2 h-4 w-4" />
//               Export Record
//             </DropdownMenuItem>
//             <DropdownMenuSeparator />
//             <DropdownMenuItem className="text-red-600">
//               <Trash2 className="mr-2 h-4 w-4" />
//               Delete Archive
//             </DropdownMenuItem>
//           </DropdownMenuContent>
//         </DropdownMenu>
//       )
//     }
//   ];

//   const validationColumns = [
//     { key: 'thesisId', header: 'Thesis ID' },
//     { key: 'validationId', header: 'Validation ID' },
//     { 
//       key: 'approved', 
//       header: 'Status', 
//       render: (item) => (
//         <Badge variant={item.approved ? "default" : "destructive"}>
//           {item.approved ? 'Approved' : 'Rejected'}
//         </Badge>
//       )
//     },
//     { key: 'validationDate', header: 'Date' },
//     { key: 'remarks', header: 'Remarks' },
//     {
//       key: 'actions',
//       header: 'Actions',
//       render: (item) => (
//         <DropdownMenu>
//           <DropdownMenuTrigger asChild>
//             <Button variant="ghost" className="h-8 w-8 p-0">
//               <MoreHorizontal className="h-4 w-4" />
//             </Button>
//           </DropdownMenuTrigger>
//           <DropdownMenuContent align="end">
//             <DropdownMenuItem onClick={() => {setSelectedRecord(item); setShowDetails(true);}}>
//               <Eye className="mr-2 h-4 w-4" />
//               View Details
//             </DropdownMenuItem>
//             <DropdownMenuItem>
//               <Download className="mr-2 h-4 w-4" />
//               Export Record
//             </DropdownMenuItem>
//           </DropdownMenuContent>
//         </DropdownMenu>
//       )
//     }
//   ];

//   const secretaryColumns = [
//     { key: 'thesisId', header: 'Thesis ID' },
//     { key: 'secretaryId', header: 'Secretary ID' },
//     { key: 'validationId', header: 'Validation ID' },
//     { key: 'archiveNumber', header: 'Archive Number' },
//     { key: 'commissionArchiveNumber', header: 'Commission Archive' },
//     { key: 'validationDate', header: 'Date' },
//     { key: 'remarks', header: 'Remarks' },
//     {
//       key: 'actions',
//       header: 'Actions',
//       render: (item) => (
//         <DropdownMenu>
//           <DropdownMenuTrigger asChild>
//             <Button variant="ghost" className="h-8 w-8 p-0">
//               <MoreHorizontal className="h-4 w-4" />
//             </Button>
//           </DropdownMenuTrigger>
//           <DropdownMenuContent align="end">
//             <DropdownMenuItem onClick={() => {setSelectedRecord(item); setShowDetails(true);}}>
//               <Eye className="mr-2 h-4 w-4" />
//               View Details
//             </DropdownMenuItem>
//             <DropdownMenuItem>
//               <Download className="mr-2 h-4 w-4" />
//               Export Record
//             </DropdownMenuItem>
//           </DropdownMenuContent>
//         </DropdownMenu>
//       )
//     }
//   ];

//   return (
//     <div className="space-y-6">
//       {/* Header */}
//       <div className="flex items-center justify-between">
//         <div>
//           <h1 className="text-3xl font-bold text-foreground">Archive Management</h1>
//           <p className="text-muted-foreground mt-1">
//             Archived thesis records and administrative validations
//             <span className="block text-xs mt-1">
//               Last updated: {lastUpdated.toLocaleString()}
//             </span>
//           </p>
//         </div>
//         <Button>
//           <RefreshCw className="mr-2 h-4 w-4" />
//           Refresh Data
//         </Button>
//       </div>

//       {/* Live Data Indicator */}
//       <Alert>
//         <Database className="h-4 w-4" />
//         <AlertDescription>
//           This archive automatically updates when thesis administration actions are performed. 
//           Currently managing {getTotalCount('all')} archived records across all categories.
//         </AlertDescription>
//       </Alert>

//       {/* Stats Overview */}
//       <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
//         <Card>
//           <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
//             <CardTitle className="text-sm font-medium">Archived Theses</CardTitle>
//             <Archive className="h-4 w-4 text-muted-foreground" />
//           </CardHeader>
//           <CardContent>
//             <div className="text-2xl font-bold">{data.archivedTheses.length}</div>
//             <p className="text-xs text-muted-foreground">
//               Completed thesis archives
//             </p>
//           </CardContent>
//         </Card>
        
//         <Card>
//           <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
//             <CardTitle className="text-sm font-medium">Admin Validations</CardTitle>
//             <Shield className="h-4 w-4 text-muted-foreground" />
//           </CardHeader>
//           <CardContent>
//             <div className="text-2xl font-bold">{data.adminValidations.length}</div>
//             <p className="text-xs text-muted-foreground">
//               Administrative reviews
//             </p>
//           </CardContent>
//         </Card>
        
//         <Card>
//           <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
//             <CardTitle className="text-sm font-medium">Commission Records</CardTitle>
//             <CheckCircle className="h-4 w-4 text-muted-foreground" />
//           </CardHeader>
//           <CardContent>
//             <div className="text-2xl font-bold">
//               {data.commissionValidations.length + data.secondaryCommissionValidations.length}
//             </div>
//             <p className="text-xs text-muted-foreground">
//               Commission validations
//             </p>
//           </CardContent>
//         </Card>
        
//         <Card>
//           <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
//             <CardTitle className="text-sm font-medium">Secretary Records</CardTitle>
//             <User className="h-4 w-4 text-muted-foreground" />
//           </CardHeader>
//           <CardContent>
//             <div className="text-2xl font-bold">
//               {data.secretaryValidations.length + 
//                data.secondSecretaryPhases.length + 
//                data.thirdSecretaryPhases.length + 
//                data.fourthSecretaryPhases.length}
//             </div>
//             <p className="text-xs text-muted-foreground">
//               Secretary processes
//             </p>
//           </CardContent>
//         </Card>
//       </div>

//       {/* Search and Filters */}
//       <Card>
//         <CardContent className="pt-6">
//           <div className="flex flex-col md:flex-row gap-4">
//             <div className="flex-1">
//               <div className="relative">
//                 <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
//                 <Input
//                   placeholder="Search archives by thesis ID, administrator, remarks..."
//                   value={searchTerm}
//                   onChange={(e) => setSearchTerm(e.target.value)}
//                   className="pl-10"
//                 />
//               </div>
//             </div>
//             <div className="w-full md:w-64">
//               <select
//                 value={selectedCategory}
//                 onChange={(e) => setSelectedCategory(e.target.value)}
//                 className="w-full p-2 border rounded-md"
//               >
//                 <option value="all">All Categories</option>
//                 <option value="archivedTheses">Archived Theses</option>
//                 <option value="adminValidations">Admin Validations</option>
//                 <option value="commissionValidations">Commission Validations</option>
//                 <option value="secretaryValidations">Secretary Validations</option>
//                 <option value="secondSecretaryPhases">Second Secretary Phase</option>
//                 <option value="thirdSecretaryPhases">Third Secretary Phase</option>
//                 <option value="fourthSecretaryPhases">Fourth Secretary Phase</option>
//                 <option value="secondaryCommissionValidations">Secondary Commission</option>
//               </select>
//             </div>
//           </div>
//         </CardContent>
//       </Card>

//       {/* Data Tables */}
//       <div className="space-y-6">
//         {(selectedCategory === 'all' || selectedCategory === 'archivedTheses') && (
//           <DataTable 
//             data={filteredData.archivedTheses || []}
//             columns={archiveColumns}
//             title="Archived Theses"
//             emptyMessage="No archived theses yet. Archives will appear here when theses are archived through the Administration page."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'adminValidations') && (
//           <DataTable 
//             data={filteredData.adminValidations || []}
//             columns={validationColumns}
//             title="Administrative Validations"
//             emptyMessage="No administrative validations recorded yet."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'commissionValidations') && (
//           <DataTable 
//             data={filteredData.commissionValidations || []}
//             columns={validationColumns}
//             title="Commission Validations"
//             emptyMessage="No commission validations recorded yet."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'secretaryValidations') && (
//           <DataTable 
//             data={filteredData.secretaryValidations || []}
//             columns={secretaryColumns}
//             title="Secretary Validations (First Phase)"
//             emptyMessage="No secretary validations recorded yet."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'secondSecretaryPhases') && (
//           <DataTable 
//             data={filteredData.secondSecretaryPhases || []}
//             columns={secretaryColumns}
//             title="Secretary Validations (Second Phase)"
//             emptyMessage="No second phase secretary validations recorded yet."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'thirdSecretaryPhases') && (
//           <DataTable 
//             data={filteredData.thirdSecretaryPhases || []}
//             columns={secretaryColumns}
//             title="Secretary Validations (Third Phase)"
//             emptyMessage="No third phase secretary validations recorded yet."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'fourthSecretaryPhases') && (
//           <DataTable 
//             data={filteredData.fourthSecretaryPhases || []}
//             columns={secretaryColumns}
//             title="Secretary Validations (Fourth Phase)"
//             emptyMessage="No fourth phase secretary validations recorded yet."
//           />
//         )}

//         {(selectedCategory === 'all' || selectedCategory === 'secondaryCommissionValidations') && (
//           <DataTable 
//             data={filteredData.secondaryCommissionValidations || []}
//             columns={validationColumns}
//             title="Secondary Commission Validations"
//             emptyMessage="No secondary commission validations recorded yet."
//           />
//         )}
//       </div>

//       {/* Detail Modal (simplified - you can expand this) */}
//       {showDetails && selectedRecord && (
//         <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
//           <Card className="max-w-2xl w-full mx-4 max-h-[80vh] overflow-y-auto">
//             <CardHeader>
//               <CardTitle className="flex items-center justify-between">
//                 Archive Record Details
//                 <Button variant="ghost" onClick={() => setShowDetails(false)}>
//                   ×
//                 </Button>
//               </CardTitle>
//             </CardHeader>
//             <CardContent>
//               <div className="space-y-4">
//                 {Object.entries(selectedRecord).map(([key, value]) => (
//                   <div key={key} className="grid grid-cols-3 gap-4">
//                     <Label className="font-medium capitalize">
//                       {key.replace(/([A-Z])/g, ' $1').toLowerCase()}:
//                     </Label>
//                     <div className="col-span-2">
//                       {typeof value === 'boolean' ? 
//                         <Badge variant={value ? "default" : "secondary"}>
//                           {value ? 'Yes' : 'No'}
//                         </Badge> : 
//                         String(value)
//                       }
//                     </div>
//                   </div>
//                 ))}
//               </div>
//             </CardContent>
//           </Card>
//         </div>
//       )}
//     </div>
//   );
// }
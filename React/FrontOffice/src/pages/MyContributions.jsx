import React from 'react';

const mockContributions = [
  { id: 1, project: 'Help Build a School', amount: 100, date: '2024-05-01' },
  { id: 2, project: 'Clean Water Initiative', amount: 50, date: '2024-05-10' },
  { id: 3, project: 'Music for All', amount: 30, date: '2024-06-01' },
];

const MyContributions = () => (
  <div className="max-w-2xl mx-auto py-10">
    <h1 className="text-3xl font-bold mb-6">My Contributions</h1>
    <table className="w-full bg-white rounded shadow">
      <thead>
        <tr className="bg-gray-100">
          <th className="p-3 text-left">Project</th>
          <th className="p-3 text-left">Amount (TND)</th>
          <th className="p-3 text-left">Date</th>
        </tr>
      </thead>
      <tbody>
        {mockContributions.map(c => (
          <tr key={c.id} className="border-t">
            <td className="p-3">{c.project}</td>
            <td className="p-3">{c.amount}</td>
            <td className="p-3">{c.date}</td>
          </tr>
        ))}
      </tbody>
    </table>
    <div className="mt-6 text-yellow-700 bg-yellow-100 p-4 rounded">Actions are disabled in demo mode.</div>
  </div>
);

export default MyContributions; 
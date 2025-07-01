import React from 'react';

const mockCampaigns = [
  { id: 1, title: 'Help Build a School', status: 'Active', raised: 4000, goal: 10000 },
  { id: 2, title: 'Music for All', status: 'Completed', raised: 6000, goal: 6000 },
];

const MyCampaigns = () => (
  <div className="max-w-2xl mx-auto py-10">
    <h1 className="text-3xl font-bold mb-6">My Campaigns</h1>
    <table className="w-full bg-white rounded shadow">
      <thead>
        <tr className="bg-gray-100">
          <th className="p-3 text-left">Title</th>
          <th className="p-3 text-left">Status</th>
          <th className="p-3 text-left">Raised</th>
          <th className="p-3 text-left">Goal</th>
        </tr>
      </thead>
      <tbody>
        {mockCampaigns.map(c => (
          <tr key={c.id} className="border-t">
            <td className="p-3">{c.title}</td>
            <td className="p-3">{c.status}</td>
            <td className="p-3">{c.raised}</td>
            <td className="p-3">{c.goal}</td>
          </tr>
        ))}
      </tbody>
    </table>
    <div className="mt-6 text-yellow-700 bg-yellow-100 p-4 rounded">Actions are disabled in demo mode.</div>
  </div>
);

export default MyCampaigns; 
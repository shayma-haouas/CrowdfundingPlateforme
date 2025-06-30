import React from 'react';

const mockUser = {
  firstName: 'Amina',
  lastName: 'Ben Salah',
  email: 'amina.bensalah@email.com',
  phone: '+216 12 345 678',
  avatar: '/assets/user1.jpg',
};

const Profile = () => (
  <div className="max-w-lg mx-auto py-10">
    <h1 className="text-3xl font-bold mb-6">My Profile</h1>
    <div className="bg-white rounded shadow p-6 flex items-center gap-6">
      <img src={mockUser.avatar} alt="avatar" className="w-24 h-24 rounded-full object-cover border" />
      <div>
        <div className="mb-2"><strong>Name:</strong> {mockUser.firstName} {mockUser.lastName}</div>
        <div className="mb-2"><strong>Email:</strong> {mockUser.email}</div>
        <div className="mb-2"><strong>Phone:</strong> {mockUser.phone}</div>
        <button className="mt-4 px-4 py-2 bg-gray-400 text-white rounded cursor-not-allowed" disabled>Edit Profile (Demo Only)</button>
      </div>
    </div>
  </div>
);

export default Profile; 
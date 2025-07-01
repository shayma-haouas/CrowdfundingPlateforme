import axios from 'axios';

const KONNECT_API_URL = 'https://api.sandbox.konnect.network/api/v2';
const KONNECT_API_KEY = import.meta.env.VITE_KONNECT_API_KEY;

export const paymentService = {
  // Initialize a payment
  initializePayment: async () => { throw new Error('Payments are disabled in demo mode.'); },

  // Verify payment status
  verifyPayment: async () => { throw new Error('Payments are disabled in demo mode.'); },
};
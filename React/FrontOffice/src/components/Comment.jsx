// pfa-project/src/components/CommentSection.jsx
import React, { useState, useEffect } from 'react';
import io from 'socket.io-client';
import { API_URL, commentService } from '../services/api'; // Import commentService
const placeholderAvatar = 'https://placehold.co/40?text=👤';




const CommentSection = ({ projectId }) => {
  const [comments, setComments] = useState([]);
  const [newCommentText, setNewCommentText] = useState('');
  const [isSubmittingComment, setIsSubmittingComment] = useState(false);
  const [commentsLoading, setCommentsLoading] = useState(true);
  const [commentsError, setCommentsError] = useState(null);
  // --------------------------

  useEffect(() => {
    if (!projectId) return; 

    let socket; 

    const loadCommentsAndSetupSocket = async () => {
      setCommentsLoading(true);
      setCommentsError(null);
      try {
        const commentsData = await commentService.getCommentsForProject(projectId);
        setComments(commentsData);
        setCommentsLoading(false);

        // --- Socket.IO Setup ---
        // Connect to the Socket.IO server using the API_URL
        socket = io(API_URL);
        console.log(`Attempting to connect socket to: ${API_URL}`);

        // Join the specific project room for real-time updates
        socket.emit('joinProjectRoom', projectId);
        console.log(`Attempting to join project room: ${projectId}`);

        // Listen for the 'newComment' event from the backend
socket.on('newComment', (comment) => {
  console.log('Received new comment:', comment);
  setComments(prevComments => {
    const alreadyExists = prevComments.some(c => c._id === comment._id);
    return alreadyExists ? prevComments : [...prevComments, comment];
  });
});

        // Basic error handling and connection logging for socket
        socket.on('connect', () => { console.log('Socket.IO connected'); });
        socket.on('disconnect', (reason) => { console.log('Socket.IO disconnected:', reason); });
        socket.on('connect_error', (err) => { console.error('Socket.IO connection error:', err); setCommentsError('Failed to connect for real-time updates.'); });

        // ------------------------

      } catch (err) {
        console.error("Error loading comments or setting up socket:", err);
        setCommentsError(err.message || 'Failed to load comments.');
        setCommentsLoading(false);
      }
    };

    loadCommentsAndSetupSocket();

    // --- Socket.IO Cleanup ---
    // Disconnect from Socket.IO and leave the room when the component unmounts
    return () => {
      if (socket) {
        console.log(`Attempting to leave project room: ${projectId}`);
        socket.emit('leaveProjectRoom', projectId); // Tell the server we're leaving the room
        socket.disconnect(); // Disconnect the socket connection
        console.log('Socket.IO client disconnected');
      }
    };
    // ------------------------

  }, [projectId]); // Dependency array: Rerun effect if projectId prop changes


  // --- Handle Comment Submission ---
  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    // Prevent submission if text is empty or submission is in progress
    if (!newCommentText.trim() || isSubmittingComment) {
      return;
    }

    setIsSubmittingComment(true);

    // Get the current logged-in user's ID from localStorage
    const authData = localStorage.getItem('authData');
    let authorId = null;
    if (authData) {
        try {
            const parsed = JSON.parse(authData);
            // *** Adjust this line based on how your authData JSON is structured ***
            authorId = parsed.user?._id; // Assuming user object with _id
        } catch (error) {
            console.error('Error parsing authData for author ID:', error);
        }
    }

    if (!authorId) {
        alert("You need to be logged in to post a comment.");
        setIsSubmittingComment(false);
        return;
    }

    // Ensure project ID is available (should be via prop, but defensive check)
     if (!projectId) {
         alert("Project not loaded, cannot post comment.");
         setIsSubmittingComment(false);
         return;
     }


    try {
      // Call the API function to create the comment using the service function
      // The real-time update will come via the Socket.IO listener in the useEffect
      await commentService.createComment({ // Use the service function
        text: newCommentText,
        authorId: authorId,
        projectId: projectId, // Use the projectId prop
      });
      console.log('📤 Sent comment to backend');


      setNewCommentText(''); // Clear the input field
      // The new comment will be added to state by the 'newComment' Socket.IO listener

    } catch (error) {
      console.error('Error posting comment:', error);
      alert('Failed to post comment.');
    } finally {
      setIsSubmittingComment(false); // Re-enable the button
    }
  };
  // ----------------------------------


  // --- Render Comment Section UI ---
  return (
    <div className="mt-8 bg-white rounded-xl shadow p-6">
      <h2 className="text-2xl font-bold mb-4">Comments (0)</h2>
      <div className="mb-4 p-4 bg-yellow-100 border border-yellow-400 text-yellow-800 rounded">
        Comments are disabled in demo mode.
      </div>
    </div>
  );
  // --------------------------------
};

export default CommentSection; 
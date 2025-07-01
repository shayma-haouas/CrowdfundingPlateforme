const schoolImage = '/assets/school.webp';
const medicalAidImage = '/assets/medical.jpg';
const cleanWaterImage = '/assets/clean.jpg'

const campaigns = [
  {
    id: 1,
    title: "Help Build a School",
    image: schoolImage,
    description: "Support education for underprivileged children in rural areas. Your donation will help us build classrooms, provide supplies, and hire teachers.",
    raised: 4000,
    goal: 10000,
    category: "Education",
    deadline: "2024-08-15",
    creator: { firstName: "Amina", lastName: "Ben Salah", avatar: "/assets/user1.jpg" },
    currentAmount: 4000,
    goalAmount: 10000,
    backers: 32
  },
  {
    id: 2,
    title: "Medical Aid for Refugees",
    image: medicalAidImage,
    description: "Providing essential healthcare and medicines for displaced families in crisis zones.",
    raised: 7500,
    goal: 15000,
    category: "Health",
    deadline: "2024-07-30",
    creator: { firstName: "Youssef", lastName: "Trabelsi", avatar: "/assets/user1.jpg" },
    currentAmount: 7500,
    goalAmount: 15000,
    backers: 54
  },
  {
    id: 3,
    title: "Clean Water Initiative",
    image: cleanWaterImage,
    description: "Ensuring access to clean drinking water in rural areas by building wells and water filtration systems.",
    raised: 3000,
    goal: 5000,
    category: "Environment",
    deadline: "2024-09-10",
    creator: { firstName: "Fatma", lastName: "Khaled", avatar: "/assets/user1.jpg" },
    currentAmount: 3000,
    goalAmount: 5000,
    backers: 21
  },
  {
    id: 4,
    title: "Solidarity Food Parcels",
    image: '/assets/solidarity.jpg',
    description: "Distribute food parcels to families in need during the holiday season. Every donation helps feed a family.",
    raised: 5200,
    goal: 8000,
    category: "Solidarity",
    deadline: "2024-08-01",
    creator: { firstName: "Omar", lastName: "Mansouri", avatar: "/assets/user1.jpg" },
    currentAmount: 5200,
    goalAmount: 8000,
    backers: 40
  },
  {
    id: 5,
    title: "Music for All",
    image: '/assets/music.jpg',
    description: "Support music education for children by providing instruments and lessons in underprivileged communities.",
    raised: 2100,
    goal: 6000,
    category: "Culture",
    deadline: "2024-10-05",
    creator: { firstName: "Sami", lastName: "Gharbi", avatar: "/assets/user1.jpg" },
    currentAmount: 2100,
    goalAmount: 6000,
    backers: 18
  },
  {
    id: 6,
    title: "Flat for Orphans",
    image: '/assets/flat.jpg',
    description: "Help us provide a safe and comfortable home for orphaned children. Your support will go towards rent and essential furnishings.",
    raised: 6700,
    goal: 12000,
    category: "Housing",
    deadline: "2024-09-20",
    creator: { firstName: "Leila", lastName: "Zribi", avatar: "/assets/user1.jpg" },
    currentAmount: 6700,
    goalAmount: 12000,
    backers: 27
  }
];

export default campaigns;

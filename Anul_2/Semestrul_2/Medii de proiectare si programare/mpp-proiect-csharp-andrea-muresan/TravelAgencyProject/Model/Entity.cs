using System;

namespace Model
{
    [Serializable]
    public class Entity<TId>
    {
        public TId Id { get; set; }
    }
}
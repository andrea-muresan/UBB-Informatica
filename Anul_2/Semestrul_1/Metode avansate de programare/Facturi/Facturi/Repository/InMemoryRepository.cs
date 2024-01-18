using System.Runtime.InteropServices;
using Facturi.Domain;

namespace Facturi.Repository;

public class InMemoryRepository<TId, TE> : IRepository<TId, TE> where TE : Entity<TId>
{
    private Dictionary<TId, TE> _dictionary = new Dictionary<TId, TE>();

    public IEnumerable<TE> FindAll()
    {
        return _dictionary.Values;
    }
    
    public TE FindOne(TId id)
    {
        if (_dictionary.ContainsKey(id))
            return _dictionary[id];
        return null;
    }
    public TE Save(TE entity)
    {
        _dictionary[entity.Id] = entity;
        return entity;
    }
}
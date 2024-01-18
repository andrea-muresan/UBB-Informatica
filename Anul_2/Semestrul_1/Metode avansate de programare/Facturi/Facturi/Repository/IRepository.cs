using Facturi.Domain;

namespace Facturi.Repository;

public interface IRepository<TId, TE> where TE: Entity<TId>
{
    IEnumerable<TE> FindAll();
}
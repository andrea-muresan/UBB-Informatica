
using System.Collections.Generic;
using travelAgency.domain;

namespace travelAgency.repository
{

    public interface ICrudRepository<TId, TE> where TE : Entity<TId>
    {
        IEnumerable<TE> FindAll();

        TE? FindOne(TId id);

        void Save(TE entity);

        void Delete(TId id);

        void Update(TE entity);
    }
}